package com.sparta.msa_exam.order.core;

import com.sparta.msa_exam.order.client.ProductResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long product_id;
    private String product_name;
    private Integer product_price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Long productId, ProductResponseDto product, Order order) {
        product_id = productId;
        product_name = product.getName();
        product_price = product.getSupply_price();
        this.order = order;
        order.getOrderItems().add(this);

    }
}