package com.example.msa.practice.product.core;

import com.example.msa.practice.product.products.ProductRequestDto;
import com.example.msa.practice.product.products.redis.ProductRedis;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private Long price;
    private Integer quantity;
    private String description;


    public Product(ProductRequestDto dto) {
        name = dto.getName();
        price = dto.getPrice();
        quantity = dto.getQuantity();
        description = dto.getDescription();
    }

    public Product updateProduct(ProductRequestDto dto) {
        name = dto.getName();
        price = dto.getPrice();
        quantity = dto.getQuantity();
        description = dto.getDescription();
        return this;
    }

    public Product deleteProduct(String username) {
        this.setDeleteAt(LocalDateTime.now());
        this.setDeleteBy(username);
        return this;
    }

    public Product updateQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Product recoveryQuantity(ProductRedis product) {
        this.quantity = product.getQuantity();
        return this;
    }

}
