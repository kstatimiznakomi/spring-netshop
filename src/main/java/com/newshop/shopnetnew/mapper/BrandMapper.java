package com.newshop.shopnetnew.mapper;

import com.newshop.shopnetnew.domain.Brand;
import com.newshop.shopnetnew.domain.Category;
import com.newshop.shopnetnew.dto.BrandDTO;
import com.newshop.shopnetnew.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BrandMapper {
    BrandMapper MAPPER = Mappers.getMapper(BrandMapper.class);
    Brand toBrand(BrandDTO dto);
    List<BrandDTO> fromBrand(List<Brand> brands);
    List<Brand> toBrandList(List<BrandDTO> brandDTOS);
    List<BrandDTO> formBrandList(List<Brand> brands);
}
