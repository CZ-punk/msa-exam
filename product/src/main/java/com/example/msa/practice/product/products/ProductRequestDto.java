package com.example.msa.practice.product.products;

import com.example.msa.practice.product.core.BaseEntity;
import lombok.Data;

@Data
public class ProductRequestDto {

    private String name;
    private Long price;
    private Integer quantity;
    private String description;

}
