package com.aoxx.oauth.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginSuccessResponse {
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiresIn;
    private long refreshTokenExpiresIn;

    public static LoginSuccessResponse create(String accessToken, String refreshToken, long accessTokenExpiresIn, long refreshTokenExpiresIn) {
        return new LoginSuccessResponse(accessToken, refreshToken, accessTokenExpiresIn, refreshTokenExpiresIn);
    }
}