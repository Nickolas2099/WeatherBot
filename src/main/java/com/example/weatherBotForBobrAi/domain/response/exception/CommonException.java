package com.example.weatherBotForBobrAi.domain.response.exception;

import com.example.weatherBotForBobrAi.domain.constant.Code;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@Builder
public class CommonException extends RuntimeException {

    private final Code code;
    private final String userMessage;
    private final String techMessage;
    private final HttpStatus httpStatus;
}
