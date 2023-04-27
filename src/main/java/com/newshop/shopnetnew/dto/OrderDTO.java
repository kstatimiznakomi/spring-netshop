package com.newshop.shopnetnew.dto;

import com.newshop.shopnetnew.domain.OrderDetails;
import com.newshop.shopnetnew.domain.OrderStatus;
import com.newshop.shopnetnew.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private Long user;
    private String created;
    private String updated;
    private String status;
    private List<OrderDetailsDTO> details;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDetailsDTO {
        private String product;
        private Double price;
        private Double amount;
        private Double sum;
        
        public OrderDetailsDTO(OrderDetails details) {
            this.product = details.getProducts().getName();
            this.price = details.getPrice().doubleValue();
            this.amount = details.getAmount().doubleValue();
            this.sum = details.getPrice().multiply(details.getAmount()).doubleValue();
        }
    }
}
