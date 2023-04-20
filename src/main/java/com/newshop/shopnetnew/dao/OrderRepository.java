package com.newshop.shopnetnew.dao;

import com.newshop.shopnetnew.domain.*;
import com.newshop.shopnetnew.dto.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findFirstByUser(User user);
    public Order getOrderByUser(User user);
    public Order getOrderByStatus(OrderStatus status);
    public OrderDTO getOrderById(Long id);
}