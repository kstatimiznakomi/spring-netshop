package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.domain.Product;
import com.newshop.shopnetnew.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();
    Page<Product> getProductsByCategory(Long categoryId, int pageNumber);
    Page<Product> getProductsByBrand(Long brandId, int pageNumber);
    Page<Product> getAllPage(int page);
    Page<Product> getProductsByName(String productName, int pageNumber);
    void addToUserBucket(Long productId, String username);

    Product getProductByName(String name);

    void addToUserOrder(Long productId, String username);
    void deleteFromUserOrder(Long productId, String username);
    void deleteFromUserBucket(Long productId, String username);
    void addProduct(String name, Double price, String img);
    void deleteProduct(ProductDTO dto);
    ProductDTO getById(Long id);
}
