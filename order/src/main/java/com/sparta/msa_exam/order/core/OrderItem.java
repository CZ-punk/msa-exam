package com.sparta.msa_exam.order.core;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    private String name;
//    private Integer quantity;
//    private Long totalPrice;
    private Integer product_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

//    public OrderItem(String name, Integer quantity, Long price) {
//        this.name = name;
//        this.quantity = quantity;
//        this.totalPrice = price;
//    }


    public OrderItem(Integer product_id, Order order) {
        this.product_id = product_id;
        this.order = order;
        order.getProduct_ids().add(this);
    }

}