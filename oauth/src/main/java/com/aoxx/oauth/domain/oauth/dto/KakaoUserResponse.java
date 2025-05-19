package com.aoxx.oauth.domain.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoUserResponse {
    private long id;

    @JsonProperty("properties")
    private Properties properties;

    @Getter
    public static class Properties {
        private String nickname;
    }
}
