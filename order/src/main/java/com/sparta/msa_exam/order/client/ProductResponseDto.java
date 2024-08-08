package com.sparta.msa_exam.order.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private String name;
    private Integer supply_price;

//    private Integer quantity;
//    private String description;

//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//
//    private String createdBy;
//    private String updatedBy;

}
