package com.sparta.msa_exam.order.exception;

import jakarta.ws.rs.InternalServerErrorException;
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void CustomClientExceptionHandle(CustomClientException e) {
        log.error(e.getMessage());
    }


    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void serverError(InternalServerErrorException e) {
        log.error(e.getMessage());
    }

}
