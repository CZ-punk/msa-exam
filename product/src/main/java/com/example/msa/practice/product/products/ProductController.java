package com.example.msa.practice.product.products;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final String ROLE_USER = "ROLE_USER";


    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto,
                                            @RequestHeader(value = "X-USERNAME", required = true) String username,
                                            @RequestHeader(value = "X-ROLE", required = true) String role) {
        if (role.equals(ROLE_USER)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not Acceptable Role");
        }
        return productService.createProduct(requestDto);
    }

    // 단일 조회
    @GetMapping("/{id}")
    public ProductResponseDto findOne(@PathVariable Long id) {
        return productService.findOne(id);
    }
}
