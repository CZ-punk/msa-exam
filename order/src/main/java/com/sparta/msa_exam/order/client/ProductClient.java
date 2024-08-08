package com.sparta.msa_exam.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {


    @GetMapping("/products/{id}")
    ProductResponseDto getProduct(@PathVariable Long id);

//    @PostMapping("/product/{id}/reduceQuantity")
//    void reduceProductQuantity(@PathVariable("id") Long id, @RequestParam("quantity") Integer quantity);

//    @PostMapping("/product/recovery/redis")
//    void recovery();

//    @GetMapping("/product/clear/redis")
//    void clearRedis();

}
