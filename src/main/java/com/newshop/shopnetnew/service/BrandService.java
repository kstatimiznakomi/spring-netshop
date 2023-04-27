package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.dto.BrandDTO;
import com.newshop.shopnetnew.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {
    List<BrandDTO> getAll();
}
