package com.example.msa.practice.product.products;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "product controller")
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final String ROLE_ADMIN = "ROLE_ADMIN";


    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto, HttpServletRequest request) {
        ROLE_CHECK(request);
        return productService.createProduct(requestDto);
    }

    // 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findOne(@PathVariable Long id) {
        ProductResponseDto findOne = productService.findOne(id);
        if (findOne == null) {
            return ResponseEntity.ok(new ProductResponseDto("data to be deleted.."));
        }
        return ResponseEntity.ok(findOne);
    }

    // paging 조회 // 추후 query dsl 적용 예정
    @GetMapping
    public List<ProductResponseDto> findAll(HttpServletRequest request) {
        ROLE_CHECK(request);
        return productService.findAll();
    }

    // 등록자가 다른 ADMIN 이라도 ADMIN 은 전부 상품 UPDATE 가능
    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto updateDto, HttpServletRequest request) {
        ROLE_CHECK(request);
        return productService.updateProduct(id, updateDto);
    }

    // Admin 만
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id, HttpServletRequest request) {
        ROLE_CHECK(request);
        return productService.deleteProduct(id, request.getHeader("X-USERNAME"));
    }

    @PostMapping("/recovery/{id}")
    public ProductResponseDto recoveryProduct(@PathVariable Long id, HttpServletRequest request) {
        ROLE_CHECK(request);
        return productService.recoveryProduct(id);
    }

    @PostMapping("/{id}/reduceQuantity")
    public HttpStatus reduceQuantity(@PathVariable Long id, @RequestParam("quantity") Integer quantity) {
        return productService.reduceQuantity(id, quantity);
    }

    @GetMapping("/clear_redis")
    public void clearRedis() {
        productService.clearRedis();
    }

    public void ROLE_CHECK(HttpServletRequest request) {
        if (request.getHeader("X-ROLE").equals(ROLE_ADMIN)) {
            return;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not Acceptable Role");
    }
}
