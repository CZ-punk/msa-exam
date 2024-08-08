package com.example.msa.practice.order.orders;

import com.example.msa.practice.order.client.ProductClient;
import com.example.msa.practice.order.client.ProductResponseDto;
import com.example.msa.practice.order.core.Order;
import com.example.msa.practice.order.core.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "order service")
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Transactional
    public Map<String, Integer> createOrder(OrderRequestDto requestDtoList) {
        Order order = new Order(requestDtoList.getOrderName());
        List<Long> productIds = requestDtoList.getOrderItemList();
        Map<String, Integer> productMap = new HashMap<>();

        for (Long productId : productIds) {
            ProductResponseDto product = productClient.getProduct(productId);
            new OrderItem(productId.intValue(), order);
            productMap.put(product.getName(), product.getSupply_price());
        }
        orderRepository.save(order);
        return productMap;
    }

    @Transactional
    @CacheEvict(cacheNames = "orderCache", key = "args[0]")
    public Map<String, Integer> addProductByOrder(Long id, Long productId) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not Found Order By Id: " + id));
        ProductResponseDto product = productClient.getProduct(productId);

        Map<String, Integer> productMap = new HashMap<>();
        productMap.put(product.getName(), product.getSupply_price());
        new OrderItem(productId.intValue(), order);
        orderRepository.save(order);
        return productMap;
    }

    @Cacheable(cacheNames = "orderCache", key = "args[0]")
    public RequiredResponseDto findOne(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not Found Order By Id: " + id));

        List<Integer> product_ids = new ArrayList<>();
        for (OrderItem productId : order.getProduct_ids()) {
            product_ids.add(productId.getProduct_id());
        }
        return new RequiredResponseDto(order.getId(), product_ids);
    }


//    @Transactional
//    public List<OrderResponseDto> createOrder(List<OrderRequestDto> requestDto) {
//
//        List<OrderItem> orderItemList = new ArrayList<>();
//        Order order = new Order(OrderStatus.PREPARING);
//
//        try {
//            for (OrderRequestDto dto : requestDto) {
//                ProductResponseDto product = productClient.getProduct(dto.getItemId());
//                productClient.reduceProductQuantity(dto.getItemId(), dto.getQuantity());
////
//                OrderItem orderItem = new OrderItem(product.getName(), dto.getQuantity(), product.getPrice() * dto.getQuantity());
//                orderItem.connectOrder(order);
//                orderItemList.add(orderItem);
//            }
//        } catch (Exception e) {
//            productClient.recovery();
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        } finally {
//            productClient.clearRedis();
//        }
//        orderRepository.save(order);
//        return orderItemList.stream()
//                .map(OrderResponseDto::new)
//                .collect(Collectors.toList());
//    }
}
