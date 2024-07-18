package com.aoxx.security.exception;


public class NameExistException extends RuntimeException{
    private static final String PREFIX_MESSAGE = "This name already exists: ";

    public NameExistException(String name) {
        super(PREFIX_MESSAGE + name);
    }

}
