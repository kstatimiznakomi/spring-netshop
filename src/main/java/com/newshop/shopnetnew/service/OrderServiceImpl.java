package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.dao.OrderDetailsRepository;
import com.newshop.shopnetnew.dao.OrderRepository;
import com.newshop.shopnetnew.dao.ProductRepository;
import com.newshop.shopnetnew.domain.*;
import com.newshop.shopnetnew.dto.OrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
     private final OrderDetailsRepository orderDetailsRepository;
    private final DeliveryPointsService deliveryPointsService;
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
    public List<Order> getOrdersByPoint(Long point){
        DeliveryPoints deliveryPoints = deliveryPointsService.getPointById(point);
        List<Order> orders = deliveryPoints.getOrders();
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
        double totalPrice = orderDTO.getDetails().stream()
                .map(OrderDTO.OrderDetailsDTO::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
        orderDTO.setTotalPrice(totalPrice);
        return orderDTO;
    }

    private List<OrderDetails> getCollectRefDetailsByIds(List<Long> detailsIds) {
        return detailsIds.stream()
                .map(orderDetailsRepository::getOne)
                .collect(Collectors.toList());
    }
    @Override
    public void addProducts(Order order, List<Long> productsIds) {
        List<OrderDetails> details = order.getDetails();
        List<OrderDetails> newDetailsList = details == null ? new ArrayList<>() : new ArrayList<>(details);
        newDetailsList.addAll(getCollectRefDetailsByIds(productsIds));
        order.setDetails(newDetailsList);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }


    @Override
    public Order getOrderByStatusAndUser(OrderStatus status, User user) {
        return orderRepository.getOrderByStatusAndUser(status, user);
    }
    @Override
    public void addProductsFromProducts(Order order, Long productId) {
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
    @Transactional
    public void addToUserBucket(Long orderId, Long pointId) {
        DeliveryPoints points = deliveryPointsService.getPointById(pointId);
        deliveryPointsService.addOrders(points, Collections.singletonList(orderId));
    }

    @Override
    public void createOrder(User user){
        Order order = new Order();
        order.setCreated(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);
        order.setUser(user);
        orderRepository.save(order);
    }
    private List<OrderDetails> getCollectRefProductsByIds(List<Long> detailsIds) {
        return detailsIds.stream()
                .map(orderDetailsRepository::getOne)
                .collect(Collectors.toList());
    }
    @Override
    public void deleteProducts(Order order, List<Long> productIds) {
        order.getDetails().removeAll(Collections.singletonList(productIds));
        orderRepository.save(order);
    }
}
