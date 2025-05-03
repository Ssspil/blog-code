package com.aoxx.oauth.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginFailResponse {
    private int status;
    private String message;

    public static LoginFailResponse create(int status, String message) {
        return new LoginFailResponse(status, message);
    }
}
