package com.newshop.shopnetnew.dto;

import com.newshop.shopnetnew.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDTO {
    private Long id;
    private String title;
    private Double price;
    private BigDecimal amount;
    private Double sum;

    public OrderDetailsDTO(OrderDetailsDTO detailsDTO) {
        this.id = detailsDTO.getId();
        this.title = detailsDTO.getTitle();
        this.price = detailsDTO.getPrice();
        this.amount = new BigDecimal(1.0);
        this.sum = Double.valueOf(detailsDTO.getPrice().toString());
    }
}
