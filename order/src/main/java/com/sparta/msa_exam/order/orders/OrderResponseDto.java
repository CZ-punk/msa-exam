package com.sparta.msa_exam.order.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponseDto {

    private Long order_id;
    private String order_name;
    private List<OrderItemDto> orderItemList;

}

