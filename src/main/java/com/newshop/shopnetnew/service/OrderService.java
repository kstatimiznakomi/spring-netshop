package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.domain.Bucket;
import com.newshop.shopnetnew.domain.Order;
import com.newshop.shopnetnew.domain.User;
import com.newshop.shopnetnew.dto.BucketDTO;
import com.newshop.shopnetnew.dto.OrderDTO;
import java.util.List;

public interface OrderService {
    List<OrderDTO> getAll();
    Order getOrderByUser(User user);
    OrderDTO getOrderByUser(String name);
    void addProducts(Order order, List<Long> productIds);

    void deleteFromOrder(Long productId, String username);
}
