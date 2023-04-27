package com.newshop.shopnetnew.dao;

import com.newshop.shopnetnew.domain.Order;
import com.newshop.shopnetnew.domain.OrderDetails;
import com.newshop.shopnetnew.dto.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
