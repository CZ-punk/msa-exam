package com.example.msa.practice.product.core;

import com.example.msa.practice.product.products.ProductRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Integer supply_price;


    public Product(ProductRequestDto dto) {
        name = dto.getName();
        supply_price = dto.getSupply_price();
    }

    public Product updateProduct(ProductRequestDto dto) {
        name = dto.getName();
        supply_price = dto.getSupply_price();
        return this;
    }

//    public Product deleteProduct(String username) {
//        this.setDeleteAt(LocalDateTime.now());
//        this.setDeleteBy(username);
//        return this;
//    }

//    public Product updateQuantity(Integer quantity) {
//        this.quantity = quantity;
//        return this;
//    }

//    public Product recoveryQuantity(ProductRedis product) {
//        this.quantity = product.getQuantity();
//        return this;
//    }

}
