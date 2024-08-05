package com.example.msa.practice.order.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j(topic = "Global Exception Handler")
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void IllegalArgumentExceptionHandle(IllegalArgumentException e) {
        log.error(e.getMessage());
    }

    @ExceptionHandler(CustomClientException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void CustomClientExceptionHandle(CustomClientException e) {
        log.error(e.getMessage());
    }

}
