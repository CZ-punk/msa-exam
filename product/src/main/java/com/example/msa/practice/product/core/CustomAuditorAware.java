package com.example.msa.practice.product.core;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomAuditorAware implements AuditorAware<String> {

    @Autowired
    private HttpServletRequest request;

    @Override
    public Optional<String> getCurrentAuditor() {
//        if (request.getRequestURI().startsWith("/order")) {
//            return Optional.empty();
//        }
        return Optional.ofNullable(request.getHeader("X-USERNAME"));
    }
}