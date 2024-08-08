package com.example.msa.practice.order.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.InternalServerErrorException;
import org.springframework.stereotype.Component;

@Component
public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();
    @Override
    public Exception decode(String s, Response response) {
        if (response.status() >= 400 || response.status() < 500) {
            return new CustomClientException("Feign Client Exception: " + response.status(), response.status());
        } else if (response.status() >= 500) {
            return new InternalServerErrorException();
        }
        return defaultErrorDecoder.decode(s, response);
    }
}
