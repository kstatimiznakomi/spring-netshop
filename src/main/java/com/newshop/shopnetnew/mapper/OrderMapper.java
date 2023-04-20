package com.newshop.shopnetnew.mapper;

import com.newshop.shopnetnew.domain.Order;
import com.newshop.shopnetnew.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface OrderMapper {
    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);
    OrderDTO toOrder(OrderDTO dto);

    Order fromOrder(Order order);
    List<OrderDTO> toOrderList(List<OrderDTO> orderDTOS);
    List<Order> fromOrderList(List<Order> orders);
}
