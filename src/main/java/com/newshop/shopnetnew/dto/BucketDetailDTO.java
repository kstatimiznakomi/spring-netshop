package com.newshop.shopnetnew.dto;

import com.newshop.shopnetnew.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.function.DoubleBinaryOperator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDetailDTO {
    private Long id;
    private String title;
    private Double price;
    private BigDecimal amount;
    private Double sum;
    private String img;
    public BucketDetailDTO(Product product){
        this.id = product.getId();
        this.title = product.getName();
        this.img = product.getImg();
        this.price = product.getPrice();
        this.amount = new BigDecimal(1.0);
        this.sum = Double.valueOf(product.getPrice().toString());
    }
}
