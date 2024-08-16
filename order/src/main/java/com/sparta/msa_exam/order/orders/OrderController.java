package com.sparta.msa_exam.order.orders;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto requestDtoList) {
        return orderService.createOrder(requestDtoList);
    }

    @GetMapping("/{id}")
    public OrderResponseDto findOne(@PathVariable Long id) {
        return orderService.findOne(id);
    }

    @PutMapping("/{id}")
    public OrderResponseDto addProductByOrder(@PathVariable Long id, @RequestBody AddDto dto) {
        return orderService.addProductByOrder(id, dto.getProduct_id());
    }

    @Data
    public static class AddDto {
        private Long product_id;
    }
}


