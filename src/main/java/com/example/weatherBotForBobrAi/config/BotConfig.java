package com.example.weatherBotForBobrAi.config;

import com.example.weatherBotForBobrAi.service.LogMessageServiceImpl;
import com.example.weatherBotForBobrAi.service.WeatherApiService;
import com.example.weatherBotForBobrAi.service.WeatherBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
@Slf4j
public class BotConfig {

    private final WeatherApiService weatherApi;
    private final LogMessageServiceImpl logMessageServiceImpl;

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String token;

    @Bean
    public DefaultBotOptions defaultBotOptions() {
        return new DefaultBotOptions();
    }

    @Bean
    public WeatherBot weatherBot() {
        return new WeatherBot(botName, token, weatherApi, logMessageServiceImpl);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(WeatherBot weatherBot) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(weatherBot);
        return botsApi;
    }
}