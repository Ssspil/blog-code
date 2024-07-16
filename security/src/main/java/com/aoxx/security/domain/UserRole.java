package com.aoxx.security.domain;

import lombok.Getter;

@Getter
public enum UserRole {
    SUPER("SUPER", "최고관리자")
    , MANAGER("MANAGER", "매니저")
    , ADMIN("ADMIN", "관리자")
    , USER("USER", "일반유저");


    private String code;
    private String value;

    UserRole(String code, String value) {
        this.code = code;
        this.value = value;
    }

}
