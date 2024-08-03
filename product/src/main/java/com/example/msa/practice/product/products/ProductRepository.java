package com.example.msa.practice.product.products;

import com.example.msa.practice.product.core.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
