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
    public String deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }


//    private final String ROLE_ADMIN = "ROLE_ADMIN";

//    public void ROLE_CHECK(HttpServletRequest request) {
//        if (request.getHeader("X-ROLE").equals(ROLE_ADMIN)) {
//            return;
//        }
//        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not Acceptable Role");
//    }

    // db deleteAt by 된 부분의 복구 ( 후에 지울지도 모름 )
//    @PostMapping("/recovery/{id}")
//    public ProductResponseDto recoveryProduct(@PathVariable Long id, HttpServletRequest request) {
//        ROLE_CHECK(request);
//        return productService.recoveryProduct(id);
//    }

//    @PostMapping("/{id}/reduceQuantity")
//    public void reduceQuantity(@PathVariable Long id, @RequestParam("quantity") Integer quantity) {
////        return
//        log.info("reduce Quantity 호출!");
//        productService.reduceQuantity(id, quantity);
//    }

//    @PostMapping("/recovery/redis")
//    public void recoveryDB() {
//        log.info("recovery redis 호출!");
//        productService.recoveryDB();
//    }

//    @GetMapping("/clear/redis")
//    public void clearRedis() {
//        productService.clearRedis();
//    }

}
