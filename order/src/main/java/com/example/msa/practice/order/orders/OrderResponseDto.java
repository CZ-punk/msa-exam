package com.example.msa.practice.order.orders;

import com.example.msa.practice.order.core.OrderItem;
import jakarta.ws.rs.core.GenericType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponseDto {

    private String order_name;
    private Map<String, Number> orderItemList;




//    private Long itemId;
//    private String itemName;
//    private Integer quantity;
//    private Long totalPrice;

//    public OrderResponseDto(OrderItem orderItem) {
//        this.itemId = orderItem.getId();
//        this.itemName = orderItem.getName();
//        this.quantity = orderItem.getQuantity();
//        this.totalPrice = orderItem.getTotalPrice();
//    }
}
