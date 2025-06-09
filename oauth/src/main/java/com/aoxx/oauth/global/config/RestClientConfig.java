package com.aoxx.oauth.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .defaultHeader(MediaType.APPLICATION_JSON_VALUE)
                .baseUrl("")
                .build();
    }
}
