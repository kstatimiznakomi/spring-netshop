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

    List<Order> getOrdersByPoint(Long point);

    OrderDTO getOrderByUser(Long id);

    void addProducts(Order order, List<Long> productsIds);

    @Transactional
    void saveOrder(Order order);

    Order getOrderByStatusAndUser(OrderStatus status, User user);

    void addProductsFromProducts(Order order, Long productId);

    @Transactional
    void addToUserBucket(Long orderId, Long pointId);

    void createOrder(User user);

    void deleteProducts(Order order, List<Long> productIds);
}
