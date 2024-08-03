package com.example.msa.practice.product.core;

import com.example.msa.practice.product.products.ProductRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
}
