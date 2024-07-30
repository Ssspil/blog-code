package com.aoxx.security.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginAuthenticationException extends AuthenticationException {
    public LoginAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public LoginAuthenticationException(String msg) {
        super(msg);
    }
}
