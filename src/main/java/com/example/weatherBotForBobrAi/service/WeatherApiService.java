package com.example.weatherBotForBobrAi.service;

import com.example.weatherBotForBobrAi.domain.model.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WeatherApiService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public WeatherApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(String city) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=" + apiKey + "&units=metric";
        log.info("URL: {}", url);
        return restTemplate.getForObject(url, WeatherResponse.class);
    }
}
