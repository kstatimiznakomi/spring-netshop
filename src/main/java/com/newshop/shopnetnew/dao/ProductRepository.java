package com.newshop.shopnetnew.dao;

import com.newshop.shopnetnew.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    public Page<Product> getProductsByCategoryId(Long categoryId, Pageable pageable);
    public Page<Product> getProductsByBrandId(Long brandId, Pageable pageable);
    public Page<Product> getProductsByBrandIdAndCategoryId(Long brandId, Long categoryId, Pageable pageable);
    public Page<Product> getProductsByNameContainingIgnoreCaseAndBrandId(String name, Long brandId,  Pageable pageable);
    public Page<Product> getProductsByNameContainingIgnoreCaseAndCategoryId(String name, Long categoryId, Pageable pageable);
    public Page<Product> getProductsByBrandIdAndCategoryIdAndNameContainingIgnoreCase(
            Long brandId, Long categoryId, String name, Pageable pageable
    );
    public Product getProductByName(String name);
    public Product getProductById(Long productId);
    public Page<Product> getProductsByNameContainingIgnoreCase(String productName, Pageable pageable);
}
