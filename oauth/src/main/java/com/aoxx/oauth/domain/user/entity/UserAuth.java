package com.aoxx.oauth.domain.user.entity;

import lombok.Getter;

@Getter
public enum UserAuth {
    SUPER("SUPER", "최고관리자")
    , MANAGER("MANAGER", "관리자")
    , USER("USER", "일반유저");


    private final String code;
    private final String value;

    UserAuth(String code, String value) {
        this.code = code;
        this.value = value;
    }
}