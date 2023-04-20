package com.newshop.shopnetnew.dto;

import com.newshop.shopnetnew.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private String nickname;
    private int amountProducts;
    private Double sum;
    private OrderStatus status;
    private String personName;
    private String address;
    private List<OrderDetailsDTO> details = new ArrayList<>();

    public void oAggregate(){
        this.amountProducts = this.details.size();
        this.sum = details.stream()
                .map(OrderDetailsDTO::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

}

