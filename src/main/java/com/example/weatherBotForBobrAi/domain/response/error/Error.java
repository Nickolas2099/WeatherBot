package com.example.weatherBotForBobrAi.domain.response.error;

import com.example.weatherBotForBobrAi.domain.constant.Code;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    private Code code;
    private String userMessage;
    private String techMessage;
}
