package com.example.msa.practice.order.core;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> product_ids = new ArrayList<>();

    public Order(String name) {
        this.name = name;
    }

    //    @Enumerated(EnumType.STRING)
//    private OrderStatus status;

//    public Order(OrderStatus status) {
//        this.status = status;
//    }

}




