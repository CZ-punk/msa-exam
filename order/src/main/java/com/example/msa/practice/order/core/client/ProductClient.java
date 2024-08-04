package com.example.msa.practice.order.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductClient {


    @GetMapping("/product/{id}")
    ProductResponseDto getProduct(@PathVariable Long id);

    @PostMapping("/product/{id}/reduceQuantity")
    HttpStatus reduceProductQuantity(@PathVariable("id") Long id, @RequestParam("quantity") Integer quantity);


}
