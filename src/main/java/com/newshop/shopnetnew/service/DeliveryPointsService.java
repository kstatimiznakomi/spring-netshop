package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.domain.Bucket;
import com.newshop.shopnetnew.domain.DeliveryPoints;
import com.newshop.shopnetnew.dto.PointDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface DeliveryPointsService {
    void addOrders(DeliveryPoints point, List<Long> productIds);

    DeliveryPoints getPointById(Long id);
    List<PointDTO> getAll();
}
