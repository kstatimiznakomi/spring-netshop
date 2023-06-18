package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.dao.DeliveryPointsRepository;
import com.newshop.shopnetnew.dto.PointDTO;
import com.newshop.shopnetnew.mapper.PointMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointServiceImpl implements PointService {
    private final PointMapper mapper = PointMapper.MAPPER;
    private final DeliveryPointsRepository deliveryPointsRepository;

    public PointServiceImpl(DeliveryPointsRepository deliveryPointsRepository) {
        this.deliveryPointsRepository = deliveryPointsRepository;
    }

    @Override
    public List<PointDTO> getAll() {
        return mapper.formPointList(deliveryPointsRepository.findAll());
    }
}
