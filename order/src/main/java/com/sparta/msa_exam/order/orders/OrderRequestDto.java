package com.sparta.msa_exam.order.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    private String orderName;
    private List<Long> orderItemList;


}
