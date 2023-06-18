package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.dao.DeliveryPointsRepository;
import com.newshop.shopnetnew.dao.OrderRepository;
import com.newshop.shopnetnew.domain.DeliveryPoints;
import com.newshop.shopnetnew.domain.Order;
import com.newshop.shopnetnew.dto.PointDTO;
import com.newshop.shopnetnew.mapper.PointMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryPointsServiceImpl implements DeliveryPointsService {
    private final DeliveryPointsRepository deliveryPointsRepository;
    private final OrderRepository orderRepository;
    private final PointMapper mapper = PointMapper.MAPPER;

    public DeliveryPointsServiceImpl(DeliveryPointsRepository deliveryPointsRepository, OrderRepository orderRepository) {
        this.deliveryPointsRepository = deliveryPointsRepository;
        this.orderRepository = orderRepository;
    }
    private List<Order> getCollectRefProductsByIds(List<Long> ordersIds) {
        return ordersIds.stream()
                .map(orderRepository::getOne)
                .collect(Collectors.toList());
    }
    @Override
    public void addOrders(DeliveryPoints point, List<Long> ordersIds) {
        List<Order> orders = point.getOrders();
        List<Order> newOrderList = orders == null ? new ArrayList<>() : new ArrayList<>(orders);
        newOrderList.addAll(getCollectRefProductsByIds(ordersIds));
        point.setOrders(newOrderList);
        deliveryPointsRepository.save(point);
    }

    @Override
    public DeliveryPoints getPointById(Long id){
        return deliveryPointsRepository.getDeliveryPointsById(id);
    }

    @Override
    public List<PointDTO> getAll() {
        return mapper.fromPoint(deliveryPointsRepository.findAll());
    }
}
