package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.domain.Bucket;
import com.newshop.shopnetnew.domain.Order;
import com.newshop.shopnetnew.domain.Product;
import com.newshop.shopnetnew.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();
    Page<Product> getAllPage(int page);
    void addToUserBucket(Long productId, String username);


    void addToUserOrder(Long productId, String username);


    void deleteFromUserOrder(Long productId, String username);

    void deleteFromUserBucket(Long productId, String username);


    void addProduct(ProductDTO dto);
    void deleteProduct(ProductDTO dto);
    ProductDTO getById(Long id);
}
