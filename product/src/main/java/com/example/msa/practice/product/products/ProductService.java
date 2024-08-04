package com.example.msa.practice.product.products;

import com.example.msa.practice.product.core.Product;
import com.example.msa.practice.product.products.redis.ProductRedis;
import com.example.msa.practice.product.products.redis.ProductRedisRepository;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductRedisRepository productRedisRepository;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto productDto) {
        Product product = new Product(productDto);
        Product saveProduct = productRepository.save(product);
        return new ProductResponseDto(saveProduct);
    }

    public ProductResponseDto findOne(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Not Find Product By Id"));
        if (product.getDeleteAt() != null || product.getDeleteBy() != null) {
            return null;
        }
        return new ProductResponseDto(product);
    }

    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream()
                .filter(product -> {
                    if (product.getDeleteAt() == null || product.getDeleteBy() == null)
                        return true;
                    return false;
                })
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductRequestDto updateDto) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Not Found Product By Id: " + id));
        Product saveProduct = productRepository.saveAndFlush(product.updateProduct(updateDto));
        log.info("product service updateProduct(): {}", saveProduct);
        log.info("product service updateProduct(): {}", new ProductResponseDto(saveProduct));

        return new ProductResponseDto(saveProduct);
    }

    @Transactional
    public String deleteProduct(Long id, String deleteBy) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Not Found Product By Id: " + id));
        Product deletedProduct = productRepository.save(product.deleteProduct(deleteBy));
        return "deleted by " + deletedProduct.getDeleteBy() + ", delete request at : " + deletedProduct.getDeleteAt();
        // 추후 Scheduler 를 통해 삭제 예정 ( DeletedBy , At 값이 존재할 때 )

    }

    @Transactional
    public ProductResponseDto recoveryProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Not Found Product By Id: " + id));

        if (product.getDeleteAt() != null || product.getDeleteBy() != null) {
            product.setDeleteBy(null);
            product.setDeleteAt(null);
            productRepository.save(product);
        }
        return new ProductResponseDto(product);
    }

    // TODO: 잠깐 Redis 에 저장하고,,,,
    @Transactional
    public HttpStatus reduceQuantity(Long id, Integer quantity) {
        try {
            Product product = productRepository.findById(id).orElseThrow(IllegalArgumentException::new);

            checkData(quantity, product);

            productRedisRepository.save(new ProductRedis(product));
            productRepository.save(product.updateQuantity(product.getQuantity() - quantity));

            return HttpStatus.OK;

        } catch (InternalServerErrorException e) {
            dataRecovery(e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            dataRecovery(e);
            return HttpStatus.BAD_REQUEST;
        }
    }

    private static void checkData(Integer quantity, Product product) {
        if (product.getDeleteBy() != null || product.getDeleteAt() != null) {
            throw new IllegalArgumentException();
        }
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException();
        }
    }

    private void dataRecovery(Exception e) {
        Iterable<ProductRedis> recoveryList = productRedisRepository.findAll();
        List<Product> result = new ArrayList<>();

        for (ProductRedis productRedis : recoveryList) {
            Product product = productRepository.findById(productRedis.getId()).orElse(null);
            result.add(product != null ? product.recoveryQuantity(productRedis) : null);
        }
        productRepository.saveAllAndFlush(result);
        productRedisRepository.deleteAll();
    }

}
