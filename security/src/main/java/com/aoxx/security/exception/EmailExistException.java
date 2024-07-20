package com.aoxx.security.exception;


public class EmailExistException extends RuntimeException{
    private static final String PREFIX_MESSAGE = "This email already exists: ";

    public EmailExistException(String email) {
        super(PREFIX_MESSAGE + email);
    }
}
