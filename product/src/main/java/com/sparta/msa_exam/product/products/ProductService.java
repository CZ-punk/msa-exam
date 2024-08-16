package com.sparta.msa_exam.product.products;

import com.sparta.msa_exam.product.core.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto productDto) {
        Product product = new Product(productDto);
        Product saveProduct = productRepository.save(product);
        return new ProductResponseDto(saveProduct);
    }

    @Cacheable(cacheNames = "productCache", key = "args[0]")
    public ProductResponseDto findOne(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not Find Product By Id"));
        return new ProductResponseDto(product);
    }

    @Cacheable(cacheNames = "productAllCache", key = "getMethod()")
    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "productAllCache", allEntries = true),
                    @CacheEvict(cacheNames = "productCache", key = "args[0]")
            }
    )
    public ProductResponseDto updateProduct(Long id, ProductRequestDto updateDto) {

        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not Found Product By Id: " + id));
        Product saveProduct = productRepository.saveAndFlush(product.updateProduct(updateDto));
        return new ProductResponseDto(saveProduct);
    }

    @Transactional
    @CacheEvict(cacheNames = "productCache", key = "args[0]")
    public ProductResponseDto deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not Found Product By Id:" + id));
        productRepository.deleteById(id);
        return new ProductResponseDto(product);

    }

}
