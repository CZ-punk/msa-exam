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
    public Map<String, Integer> createOrder(@RequestBody OrderRequestDto requestDtoList) {
        return orderService.createOrder(requestDtoList);
    }

    @GetMapping("/{id}")
    public RequiredResponseDto findOne(@PathVariable Long id) {
        return orderService.findOne(id);
    }

    @PutMapping("/{id}")
    public Map<String, Integer> addProductByOrder(@PathVariable Long id, @RequestBody AddDto dto) {
        return orderService.addProductByOrder(id, dto.getProduct_id());
    }

    @Data
    public static class AddDto {
        private Long product_id;
    }

//    private final String ROLE_ADMIN = "ROLE_ADMIN";
//    public void ROLE_CHECK(HttpServletRequest request) {
//        if (request.getHeader("X-ROLE").equals(ROLE_ADMIN)) {
//            return;
//        }
//        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not Acceptable Role");
//    }

}


