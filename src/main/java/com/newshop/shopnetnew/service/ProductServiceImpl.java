package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.dao.OrderRepository;
import com.newshop.shopnetnew.dao.ProductRepository;
import com.newshop.shopnetnew.domain.*;
import com.newshop.shopnetnew.dto.ProductDTO;
import com.newshop.shopnetnew.mapper.ProductMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;
    private final OrderService orderService;
    private final SimpMessagingTemplate template;
    public ProductServiceImpl(@Lazy ProductRepository productRepository, @Lazy UserService userService, @Lazy BucketService bucketService, @Lazy OrderService orderService, @Lazy OrderRepository orderRepository, SimpMessagingTemplate template) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
        this.orderService = orderService;
        this.template = template;
    }

    @Override
    public List<ProductDTO> getAll() {
        return mapper.fromProductList(productRepository.findAll());
    }

    @Override
    public Page<Product> getProductsByCategory(Long categoryId, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber - 1,5);
        return productRepository.getProductsByCategoryId(categoryId, page);
    }

    @Override
    public Page<Product> getProductsByBrand(Long brandId, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber - 1,5);
        return productRepository.getProductsByBrandId(brandId, page);
    }

    @Override
    public Page<Product> getAllPage(int pageNumber) {
        Pageable page = PageRequest.of(pageNumber - 1,5);
        return productRepository.findAll(page);
    }

    @Override
    public Page<Product> getProductsByName(String productName, int pageNumber){
        Pageable page = PageRequest.of(pageNumber - 1,5);
        return productRepository.getProductsByNameContainingIgnoreCase(productName, page);
    }

    @Override
    @Transactional
    public void addToUserBucket(Long productId, String username) {
        User user = userService.findByName(username);
        Bucket bucket = bucketService.getBucketByUser(user);

        bucketService.addProducts(bucket, Collections.singletonList(productId));
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.getProductByName(name);
    }

    @Override
    @Transactional
    public void addToUserOrder(Long productId, String username) {
        User user = userService.findByName(username);
        Order order = orderService.getOrderByStatusAndUser(OrderStatus.NEW, user);
        if (order == null){
            orderService.createOrder(user);
            Order orderNew = orderService.getOrderByStatusAndUser(OrderStatus.NEW, user);
            orderService.addProductsFromProducts(orderNew, productId);
        }
        else {
            orderService.addProductsFromProducts(order, productId);
        }
    }
    @Override
    @Transactional
    public void deleteFromUserBucket(Long productId, String username) {
        User user = userService.findByName(username);
        if (user == null) throw new RuntimeException("Пользователь не найден");
        Bucket bucket = bucketService.getBucketByUser(user);
        bucketService.deleteProducts(bucket, Collections.singletonList(productId));
    }

    @Override
    @Transactional
    public void deleteFromUserOrder(Long productId, String username) {
        User user = userService.findByName(username);
        if (user == null) throw new RuntimeException("Пользователь не найден");
        Order order = orderService.getOrderByStatusAndUser(OrderStatus.NEW, user);
        orderService.deleteProducts(order, Collections.singletonList(productId));
    }

    @Override
    @Transactional
    public void addProduct(String name, Double price, String img) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setImg(img);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(ProductDTO dto) {
        Product product = mapper.toProduct(dto);
        Product savedProduct = productRepository.save(product);

        template.convertAndSend("/topic/products",
                ProductMapper.MAPPER.fromProduct(savedProduct));
    }

    @Override
    public ProductDTO getById(Long id) {
        Product product = productRepository.findById(id).orElse(new Product());
        return ProductMapper.MAPPER.fromProduct(product);
    }

}