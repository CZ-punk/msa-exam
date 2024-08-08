package com.sparta.msa_exam.product.products;

import com.sparta.msa_exam.product.core.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private String name;
    private Integer supply_price;

    public ProductResponseDto(Product product) {
        this.name = product.getName();
        this.supply_price = product.getSupply_price();
    }

}
