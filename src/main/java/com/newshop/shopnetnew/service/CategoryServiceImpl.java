package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.dao.CategoriesRepository;
import com.newshop.shopnetnew.dto.CategoryDTO;
import com.newshop.shopnetnew.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryMapper mapper = CategoryMapper.MAPPER;
    private final CategoriesRepository categoriesRepository;

    public CategoryServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

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
    public CategoryDTO getById(Long id) {
        return null;
    }
}
