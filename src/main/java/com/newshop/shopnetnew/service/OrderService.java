package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.domain.Order;
import com.newshop.shopnetnew.domain.OrderStatus;
import com.newshop.shopnetnew.domain.User;
import com.newshop.shopnetnew.dto.OrderDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface OrderService {
    List<OrderDTO> getAll();
    Order getOrderByUser(User user);
    List<Order> getOrdersByUser(String name);
    OrderDTO getOrderByUser(Long id);

    @Transactional
    void saveOrder(Order order);

    Order getOrderByStatusAndUser(OrderStatus status, User user);

    void addProducts(Order order);

    void deleteProducts(Order order, List<Long> productIds, User user);
}
