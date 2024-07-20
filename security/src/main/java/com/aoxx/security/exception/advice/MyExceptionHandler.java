package com.aoxx.security.exception.advice;

import com.aoxx.security.exception.EmailExistException;
import com.aoxx.security.model.dto.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class MyExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailExistException.class)
    public ErrorResult emailDuplicationExceptionHandler(EmailExistException e){
        log.error("EmailExistException >> ", e.getMessage(), e);
        return new ErrorResult(HttpStatus.CONFLICT .value(), e.getMessage());
    }
}
