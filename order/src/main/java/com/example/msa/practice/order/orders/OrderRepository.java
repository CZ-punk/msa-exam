package com.example.msa.practice.order.orders;

import com.example.msa.practice.order.core.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
