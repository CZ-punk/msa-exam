package com.example.msa.practice.order.orders;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final String ROLE_ADMIN = "ROLE_ADMIN";


    @PostMapping
    public List<OrderResponseDto> createOrder(@RequestBody List<OrderRequestDto> requestDtoList) {
        return orderService.createOrder(requestDtoList);
    }


    public void ROLE_CHECK(HttpServletRequest request) {
        if (request.getHeader("X-ROLE").equals(ROLE_ADMIN)) {
            return;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not Acceptable Role");
    }
}
