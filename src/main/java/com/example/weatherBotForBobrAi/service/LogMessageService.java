package com.example.weatherBotForBobrAi.service;

import com.example.weatherBotForBobrAi.domain.entity.LogMessage;
import com.example.weatherBotForBobrAi.domain.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface LogMessageService {

     void saveMessage(long telegramUserId, String userMessage, String botAnswer);

    ResponseEntity<Response> getMessages();

    ResponseEntity<Response> getMessagesByUserId(Long UserId);

    ResponseEntity<Response> getPageableLogs(int page, int size);

}
