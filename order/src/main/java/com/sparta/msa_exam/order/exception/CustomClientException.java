package com.sparta.msa_exam.order.exception;

import lombok.Getter;

@Getter
public class CustomClientException extends RuntimeException {
    private final int statusCode;

    public CustomClientException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
