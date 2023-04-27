package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.domain.Category;
import com.newshop.shopnetnew.dto.CategoryDTO;
import com.newshop.shopnetnew.dto.ProductDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAll();
    List<CategoryDTO> getAllByCategory();
    void addCategory(CategoryDTO dto);
    CategoryDTO getById(Long id);
}
