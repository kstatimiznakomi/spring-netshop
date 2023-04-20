package com.newshop.shopnetnew.mapper;

import com.newshop.shopnetnew.domain.Category;
import com.newshop.shopnetnew.domain.Product;
import com.newshop.shopnetnew.dto.CategoryDTO;
import com.newshop.shopnetnew.dto.ProductDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {
    CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);
    Category toCategory(CategoryDTO dto);
    @InheritInverseConfiguration
    CategoryDTO fromCategory(Category category);
    List<Category> toCategoryList(List<CategoryDTO> categoryDTOS);
    List<CategoryDTO> formCategoryList(List<Category> categories);
}
