package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.dao.CategoriesRepository;
import com.newshop.shopnetnew.domain.Category;
import com.newshop.shopnetnew.dto.CategoryDTO;
import com.newshop.shopnetnew.mapper.CategoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryMapper mapper = CategoryMapper.MAPPER;
    private final CategoriesRepository categoriesRepository;

    @Override
    public List<CategoryDTO> getAll() {
        return mapper.fromCategory(categoriesRepository.findAll());
    }

    @Override
    public List<CategoryDTO> getAllByCategory() {
        return null;
    }

    @Override
    public void addCategory(CategoryDTO dto) {

    }

    @Override
    public Category getById(Long id) {
        return categoriesRepository.getCategoryById(id);
    }
    @Override
    public Category getByName(String name) {
        return categoriesRepository.getCategoryByName(name);
    }
}
