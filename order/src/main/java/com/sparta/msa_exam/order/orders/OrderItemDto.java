package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.core.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long product_id;
    private String product_name;
    private Integer product_price;


    public OrderItemDto(OrderItem orderItem) {
        product_id = orderItem.getProduct_id();
        product_name = orderItem.getProduct_name();
        product_price = orderItem.getProduct_price();
    }
}
