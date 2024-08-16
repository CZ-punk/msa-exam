package com.sparta.msa_exam.product.products;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "product controller")
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto, HttpServletRequest request) {
        return productService.createProduct(requestDto);
    }

    @GetMapping("/{id}")
    public ProductResponseDto findOne(@PathVariable Long id) {
        return productService.findOne(id);
    }

    @GetMapping
    public List<ProductResponseDto> findAll() {
        return productService.findAll();
    }

    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto updateDto, HttpServletRequest request) {
        return productService.updateProduct(id, updateDto);
    }

    @DeleteMapping("/{id}")
    public ProductResponseDto deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

}
