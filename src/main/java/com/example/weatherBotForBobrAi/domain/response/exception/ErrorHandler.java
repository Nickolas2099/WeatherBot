package com.example.weatherBotForBobrAi.domain.response.exception;


import com.example.weatherBotForBobrAi.domain.constant.Code;
import com.example.weatherBotForBobrAi.domain.response.error.Error;
import com.example.weatherBotForBobrAi.domain.response.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException ex) {
        log.error("common error: {}", ex.toString());
        return new ResponseEntity<>(ErrorResponse.builder()
                .error(Error.builder().code(ex.getCode()).techMessage(ex.getUserMessage())
                        .build()).build(), ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedErrorException(Exception ex) {
        ex.printStackTrace();
        log.error("internal server error: {}", ex.toString());
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder().code(Code.INTERNAL_SERVER_ERROR)
                .userMessage("Внутренняя ошибка сервиса").build()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        log.error("MissingRequestHeaderException {}", ex.toString());
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder().code(Code.MISSING_REQUEST_HEADER)
                .techMessage(ex.getMessage()).build()).build(), HttpStatus.BAD_REQUEST);
    }
}
