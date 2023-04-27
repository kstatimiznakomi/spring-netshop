package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.dao.BrandRepository;
import com.newshop.shopnetnew.dto.BrandDTO;
import com.newshop.shopnetnew.mapper.BrandMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper mapper = BrandMapper.MAPPER;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<BrandDTO> getAll() {
        return mapper.fromBrand(brandRepository.findAll());
    }
}
