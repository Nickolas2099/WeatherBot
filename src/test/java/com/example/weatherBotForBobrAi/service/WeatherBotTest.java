package com.example.weatherBotForBobrAi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WeatherBotTest {
    @MockBean
    private WeatherApiService weatherApi;

    @MockBean
    private LogMessageServiceImpl logMessageServiceImpl;

    @InjectMocks
    private WeatherBot weatherBot;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOnUpdateReceived_startCommand() {
        Update update = new Update();
        Message message = new Message();
        message.setText("/start");
        message.setChat(new Chat());
        message.getChat().setId(123456789L);
        User user = new User();
        String name = "Sergey";
        user.setUserName(name);
        message.setFrom(user);
        update.setMessage(message);

        weatherBot.onUpdateReceived(update);

        verify(logMessageServiceImpl, times(1)).saveMessage(eq(123456789L), eq("/start"),
                contains("Привет, " + name + "! Для того чтобы узнать погоду напиши /weather <город>"));
    }


    @Test
    public void testOnUpdateReceived_UnknownCommand() {
        Update update = new Update();
        Message message = new Message();
        message.setText("random text");
        Chat chat = new Chat();
        chat.setId(123456789L);
        message.setChat(chat);
        update.setMessage(message);

        weatherBot.onUpdateReceived(update);

        verify(logMessageServiceImpl, times(1)).saveMessage(eq(123456789L),
                eq("/unknown"), eq("Извините, команда не распознанна"));
    }

}
