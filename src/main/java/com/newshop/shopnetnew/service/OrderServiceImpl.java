package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.dao.OrderDetailsRepository;
import com.newshop.shopnetnew.dao.OrderRepository;
import com.newshop.shopnetnew.dao.ProductRepository;
import com.newshop.shopnetnew.domain.*;
import com.newshop.shopnetnew.dto.OrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final ProductRepository productRepository;
    @Override
    public List<OrderDTO> getAll() {
        return null;
    }

    @Override
    public Order getOrderByUser(User user) {
        return null;
    }

    @Override
    public List<Order> getOrdersByUser(String name){
        User user = userService.findByName(name);
        List<Order> orders = orderRepository.getOrdersByUser(user);
        return orders;
    }

    @Override
    public OrderDTO getOrderByUser(Long id) {
        Order order = orderRepository.getOrderById(id);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getId());
        List<OrderDTO.OrderDetailsDTO> details = order.getDetails().stream()
                .map(OrderDTO.OrderDetailsDTO::new).collect(Collectors.toList());
        orderDTO.setDetails(details);
        return orderDTO;
    }

    private List<OrderDetails> getCollectRefProductsByIds(List<Long> detailsIds) {
        return detailsIds.stream()
                .map(orderDetailsRepository::getOne)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
    }
    @Override
    public Order getOrderByStatusAndUser(OrderStatus status, User user) {
        return orderRepository.getOrderByStatusAndUser(status, user);
    }
    @Override
    public void addProducts(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getId());
        dto.setUser(order.getUser().getId());
        dto.setCreated(String.valueOf(order.getCreated()));
        dto.setStatus(order.getStatus().name());
        List<OrderDTO.OrderDetailsDTO> details = order.getDetails().stream()
                .map(OrderDTO.OrderDetailsDTO::new).collect(Collectors.toList());
        dto.setDetails(details);
    }

    @Override
    public void deleteProducts(Order order, List<Long> productIds, User user) {
        order.getDetails().removeAll(getCollectRefProductsByIds(productIds));
        orderRepository.save(order);
    }
}
