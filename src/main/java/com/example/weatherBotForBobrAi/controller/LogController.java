package com.example.weatherBotForBobrAi.controller;

import com.example.weatherBotForBobrAi.domain.response.Response;
import com.example.weatherBotForBobrAi.service.LogMessageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/bot")
public class LogController {

    private final LogMessageServiceImpl logMessageServiceImpl;

    @GetMapping("/logs")
    public ResponseEntity<Response> getLogs() {

        log.info("START endpoint getLogs");
        ResponseEntity<Response> response = logMessageServiceImpl.getMessages();
        log.info("END endpoint getLogs");
        return response;
    }


    @GetMapping("/logs/{user_id}")
    public ResponseEntity<Response> getLogsById(@PathVariable("user_id") Long userId) {

        log.info("START endpoint getLogsById");
        ResponseEntity<Response> response = logMessageServiceImpl.getMessagesByUserId(userId);
        log.info("END endpoint getLogsById");
        return response;
    }

    @GetMapping("/pageable")
    public ResponseEntity<Response> getPageableLogs(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        log.info("START endpoint getPageableLogs, page {}, size {}", page, size);
        ResponseEntity<Response> response = logMessageServiceImpl.getPageableLogs(page, size);
        log.info("END endpoint getPageableLogs");
        return response;
    }
}
