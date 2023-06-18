package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.dto.PointDTO;
import com.newshop.shopnetnew.mapper.BrandMapper;
import com.newshop.shopnetnew.mapper.PointMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PointService {
    List<PointDTO> getAll();
}
