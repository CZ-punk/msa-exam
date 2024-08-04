package com.example.msa.practice.order.core.exception;

public class CustomClientException extends RuntimeException {
    private final int statusCode;

    public CustomClientException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
