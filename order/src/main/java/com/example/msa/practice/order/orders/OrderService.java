package com.example.msa.practice.order.orders;

import com.example.msa.practice.order.core.Order;
import com.example.msa.practice.order.core.OrderItem;
import com.example.msa.practice.order.core.OrderStatus;
import com.example.msa.practice.order.core.client.ProductClient;
import com.example.msa.practice.order.core.client.ProductResponseDto;
import com.example.msa.practice.order.core.exception.CustomClientException;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "order service")
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;



    @Transactional
    public List<OrderResponseDto> createOrder(List<OrderRequestDto> requestDto) {

        List<OrderItem> orderItemList = new ArrayList<>();
        Order order = new Order(OrderStatus.PREPARING);


        for (OrderRequestDto dto : requestDto) {
            ProductResponseDto product = productClient.getProduct(dto.getItemId());
            HttpStatus status = productClient.reduceProductQuantity(dto.getItemId(), dto.getQuantity());
            log.info("Response status: {}", status);
            if (status == HttpStatus.BAD_REQUEST) {
                throw new IllegalArgumentException("Bad Request: Not Acceptable Quantity:" + dto.getQuantity());
            } else if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
                throw new InternalServerErrorException("Internal Server Error..");
            }
            OrderItem orderItem = new OrderItem(product.getName(), dto.getQuantity(), product.getPrice() * dto.getQuantity());
            orderItem.connectOrder(order);
            orderItemList.add(orderItem);
        }
        productClient.clearProductRedis();

        orderRepository.save(order);
        return orderItemList.stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }
}
