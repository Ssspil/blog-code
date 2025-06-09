package com.aoxx.oauth.domain.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@RequiredArgsConstructor
public class KakaoTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("id_token")
    private String tokenId;

    @JsonProperty("expires_in")
    private long accessTokenExpiresIn; // 초 단위

    @JsonProperty("refresh_token_expires_in")
    private long refreshTokenExpiresIn;
}

