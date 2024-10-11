package com.example.weatherBotForBobrAi.service;

import com.example.weatherBotForBobrAi.domain.model.WeatherResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherBot extends TelegramLongPollingBot {

    private String botName;
    private String token;
    private final WeatherApiService weatherApi;
    private final LogMessageServiceImpl logMessageServiceImpl;

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            log.info("USER_MESSAGE: {}", messageText);
            String botAnswer = "Извините, команда не распознанна";
            long telegramUserId = update.getMessage().getChatId();
            switch (messageText.trim()) {

                case "/start" -> botAnswer = startCommandRecieved(chatId, update.getMessage().getChat().getFirstName());

                default -> {
                    if(messageText.startsWith("/weather")) {
                        String city = extractCity(messageText);
                        if(city == null) {
                            botAnswer = "Необходимо указать город на латинице после команды /weather";
                            sendMessage(chatId, botAnswer);
                        } else {
                            try {
                                WeatherResponse weatherResponse = weatherApi.getWeather(city);
                                botAnswer = sendWeatherMessage(chatId, weatherResponse);
                            } catch(RuntimeException e) {
                                sendMessage(chatId, "Упс! город " + city + " не найден");
                                log.error("Exception: {}", e.getMessage());
                            }
                        }
                    } else {
                        botAnswer = "Извините, команда не распознанна";
                        sendMessage(chatId, botAnswer);
                    }
                }
            }
            logMessageServiceImpl.saveMessage(telegramUserId, messageText.trim(), botAnswer);
        }

    }

    private String startCommandRecieved(long chatId, String name) {
        String answer = "Привет, " + name + "! Для того чтобы узнать погоду напиши /weather <город>";
        sendMessage(chatId, answer);
        return answer;
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch(TelegramApiException exc) {
            log.error("Exception: {}", exc.getMessage());
        }
    }

    private String sendWeatherMessage(long chatId, WeatherResponse weatherResponse) {
        if (weatherResponse != null) {
            String weatherText = "Погода в городе " + weatherResponse.getWeather()[0].getDescription() + ", температура " +
                    weatherResponse.getMain().getTemp() + "°C, ощущается как " +
                    weatherResponse.getMain().getFeels_like() + "°C, влажность: " +
                    weatherResponse.getMain().getHumidity() + " г/м³, скорость ветра: " +
                    weatherResponse.getWind().getSpeed() + " м/с.";
            sendMessage(chatId, weatherText);
            return weatherText;
        } else {
            String text = "Не удалось получить данные о погоде";
            sendMessage(chatId, text);
            return text;
        }
    }

    private String extractCity(String messageText) {
        if (messageText.startsWith("/weather ")) {
            return messageText.substring(9);
        }
        return null;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {

        return token;
    }
}
