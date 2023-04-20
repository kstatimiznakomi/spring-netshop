package com.newshop.shopnetnew.dao;

import com.newshop.shopnetnew.domain.Brand;
import com.newshop.shopnetnew.domain.Category;
import com.newshop.shopnetnew.domain.Product;
import com.newshop.shopnetnew.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    List<Product> findAllByPrice(Double price);
    List<Product> findByName(String name);
}
