package com.aoxx.oauth.domain.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private long accessTokenExpiresIn; // 초 단위

    @JsonProperty("refresh_token_expires_in")
    private long refreshTokenExpiresIn;
}
