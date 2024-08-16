package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.client.ProductResponseDto;
import com.sparta.msa_exam.order.core.Order;
import com.sparta.msa_exam.order.core.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "order service")
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDtoList) {
        Order order = new Order(requestDtoList.getOrderName());
        List<Long> productIds = requestDtoList.getOrderItemList();

        for (Long productId : productIds) {
            ProductResponseDto product = productClient.getProduct(productId);
            new OrderItem(productId, product, order);
        }

        orderRepository.save(order);
        return new OrderResponseDto(order.getId(), order.getName(), createItemDtoList(order));
    }

    @Transactional
    @CachePut(cacheNames = "orderCache", key = "args[0]")
    public OrderResponseDto addProductByOrder(Long id, Long productId) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not Found Order By Id: " + id));
        List<OrderItemDto> orderItemDtoList = createItemDtoList(order);

        ProductResponseDto product = productClient.getProduct(productId);
        OrderItem orderItem = new OrderItem(productId, product, order);
        orderItemDtoList.add(new OrderItemDto(orderItem));

        return new OrderResponseDto(order.getId(), order.getName(), orderItemDtoList);
    }

    @Cacheable(cacheNames = "orderCache", key = "args[0]")
    public OrderResponseDto findOne(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not Found Order By Id: " + id));
        List<OrderItemDto> itemDtoList = createItemDtoList(order);

        return new OrderResponseDto(order.getId(), order.getName(), itemDtoList);
    }

    private List<OrderItemDto> createItemDtoList(Order order) {
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItemDtoList.add(new OrderItemDto(orderItem));
        }
        return orderItemDtoList;
    }

}
