package com.aoxx.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResult {
    private Integer code;
    private String message;
    private Map<String, Object> data;

    public ErrorResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}


