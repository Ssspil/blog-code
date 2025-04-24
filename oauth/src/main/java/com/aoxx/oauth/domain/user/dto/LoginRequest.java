package com.aoxx.oauth.domain.user.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String phoneNumber;
    private String password;
}