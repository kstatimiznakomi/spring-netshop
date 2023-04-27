package com.newshop.shopnetnew.dao;

import com.newshop.shopnetnew.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findFirstByUser(User user);
    Order getOrderByStatusAndUser(OrderStatus status, User user);
    public List<Order> getOrdersByUser(User user);
    public Order getOrderById(Long id);
}