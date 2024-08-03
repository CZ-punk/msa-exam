package com.example.msa.practice.product.products;

import com.example.msa.practice.product.core.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductRequestDto productDto) {
        Product product = new Product(productDto);
        Product saveProduct = productRepository.save(product);
        return new ProductResponseDto(saveProduct);
    }

    public ProductResponseDto findOne(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Not Find Product By Id"));
        return new ProductResponseDto(product);
    }
}
