package com.example.weatherBotForBobrAi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeatherResponse {
    private Main main;
    private Weather [] weather;
    private Wind wind;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Main {
        private float temp;
        private float feels_like;
        private float temp_min;
        private float temp_max;
        private int pressure;
        private int humidity;

    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Weather {
        private String main;
        private String description;

    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Wind {
        private float speed;
        private int deg;
    }
}
