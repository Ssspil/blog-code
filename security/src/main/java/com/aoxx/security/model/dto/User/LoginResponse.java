package com.aoxx.security.model.dto.User;

import lombok.Getter;

@Getter
public class LoginResponse {
    private int status;
    private String message;

    public LoginResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
