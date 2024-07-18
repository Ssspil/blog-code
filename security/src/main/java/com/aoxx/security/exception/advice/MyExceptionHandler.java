package com.aoxx.security.exception.advice;

import com.aoxx.security.exception.NameExistException;
import com.aoxx.security.model.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.NameAlreadyBoundException;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class MyExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(NameExistException.class)
    public ErrorResult nameDuplicationExceptionHandler(NameExistException e){
        log.error("NameExistException >> ", e.getMessage(), e);
        return new ErrorResult(HttpStatus.CONFLICT .value(), e.getMessage());
    }
}
