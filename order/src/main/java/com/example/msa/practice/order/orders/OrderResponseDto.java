package com.example.msa.practice.order.orders;

import com.example.msa.practice.order.core.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponseDto {

    private Long itemId;
    private String itemName;
    private Integer quantity;
    private Long totalPrice;

    public OrderResponseDto(OrderItem orderItem) {
        this.itemId = orderItem.getId();
        this.itemName = orderItem.getName();
        this.quantity = orderItem.getQuantity();
        this.totalPrice = orderItem.getTotalPrice();
    }
}
