package com.example.weatherBotForBobrAi.service;

import com.example.weatherBotForBobrAi.domain.constant.Code;
import com.example.weatherBotForBobrAi.domain.entity.LogMessage;
import com.example.weatherBotForBobrAi.domain.response.Response;
import com.example.weatherBotForBobrAi.domain.response.SuccessResponse;
import com.example.weatherBotForBobrAi.domain.response.error.Error;
import com.example.weatherBotForBobrAi.domain.response.error.ErrorResponse;
import com.example.weatherBotForBobrAi.repository.LogMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.http.util.TimeStamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogMessageServiceImpl implements LogMessageService {

    private final LogMessageRepository logRepo;

    public void saveMessage(long telegramUserId, String userMessage, String botAnswer) {

        LogMessage logMessage = new LogMessage();
        logMessage.setTelegramUserId(telegramUserId);
        logMessage.setUserMessage(userMessage);
        logMessage.setBotAnswer(botAnswer);
        logMessage.setMessageTime(new Timestamp(System.currentTimeMillis()));
        logRepo.save(logMessage);
    }

    public ResponseEntity<Response> getMessages() {

        return new ResponseEntity<>(SuccessResponse.builder().data(logRepo.getRecentMessages()).build(),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> getMessagesByUserId(Long userId) {
        if(userId == null || userId.toString().length() < 8) {
            return new ResponseEntity<>(ErrorResponse.builder()
                    .error(Error.builder().techMessage("Некорректный айди").code(Code.BAD_REQUEST).build()).build(),
                    HttpStatus.BAD_REQUEST);
        }


        List<LogMessage> messages = logRepo.getRecentMessagesByUserId(userId);
        if(messages.size()>0) {
            return new ResponseEntity<>(SuccessResponse.builder().data(messages).build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .error(Error.builder().techMessage("Записи не найдены").code(Code.NOT_FOUND).build()).build(),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Response> getPageableLogs(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(SuccessResponse.builder().data(logRepo.findAllMessages(pageable)).build(),
                HttpStatus.OK);
    }


}
