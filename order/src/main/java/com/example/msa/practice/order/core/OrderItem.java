package com.example.msa.practice.order.core;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private Integer quantity;
    private Long totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(String name, Integer quantity, Long price) {
        this.name = name;
        this.quantity = quantity;
        this.totalPrice = price;
    }

    public void connectOrder(Order order) {
        this.order = order;
        order.getOrderItems().add(this);
    }
}