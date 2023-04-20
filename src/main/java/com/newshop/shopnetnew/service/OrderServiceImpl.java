package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.dao.OrderRepository;
import com.newshop.shopnetnew.dao.ProductRepository;
import com.newshop.shopnetnew.domain.*;
import com.newshop.shopnetnew.dto.OrderDTO;
import com.newshop.shopnetnew.dto.OrderDetailsDTO;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final UserService userService;
    private final OrderRepository orderRepository;
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
    public OrderDTO getOrderByUser(String name) {
        User user = userService.findByName(name);
        if (user == null || user.getOrders().isEmpty()){
            return new OrderDTO();
        }

        List<Order> orders = user.getOrders();
        Order order = orders.get(orders.size() - 1);
        OrderDTO orderDTO = orderRepository.getOrderById(order.getId());

        Map<Long, OrderDetailsDTO> mapByDetailId = new HashMap<>();

        List<OrderDetailsDTO> orderDetails = orderDTO.getDetails();

        for (OrderDetailsDTO detailsDTO : orderDetails){
            OrderDetailsDTO detail = mapByDetailId.get(detailsDTO.getId());
            if(detail == null){
                mapByDetailId.put(detailsDTO.getId(), new OrderDetailsDTO(detailsDTO));
            } else {
                detail.setAmount(detail.getAmount().add(new BigDecimal(1.0)));
                detail.setSum(detail.getSum() + Double.valueOf(detailsDTO.getPrice().toString()));
            }
        }
        orderDTO.setDetails(new ArrayList<>(mapByDetailId.values()));
        orderDTO.oAggregate();
        return orderDTO;
    }

    private List<Product> getCollectRefProductsByIds(List<Long> detailsIds) {
        return detailsIds.stream()
                .map(productRepository::getOne)
                .collect(Collectors.toList());
    }

    @Override
    public void addProducts(Order order, List<Long> detailsIds) {
        List<Product> details = order.getDetails();
        List<Product> newOrderList = details == null ? new ArrayList<>() : new ArrayList<>(details);
        newOrderList.addAll(getCollectRefProductsByIds(detailsIds));
        order.setDetails(newOrderList);
        orderRepository.save(order);
    }

    @Override
    public void deleteFromOrder(Long productId, String username) {

    }
}
