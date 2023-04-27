package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.dao.OrderRepository;
import com.newshop.shopnetnew.dao.ProductRepository;
import com.newshop.shopnetnew.domain.*;
import com.newshop.shopnetnew.dto.ProductDTO;
import com.newshop.shopnetnew.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final SimpMessagingTemplate template;
    public ProductServiceImpl(ProductRepository productRepository, UserService userService, BucketService bucketService, OrderService orderService, OrderRepository orderRepository, SimpMessagingTemplate template) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.template = template;
    }

    @Override
    public List<ProductDTO> getAll() {
        return mapper.fromProductList(productRepository.findAll());
    }

    @Override
    public Page<Product> getAllPage(int pageNumber) {
        Pageable page = PageRequest.of(pageNumber - 1,5);
        return productRepository.findAll(page);
    }

    @Override
    @Transactional
    public void addToUserBucket(Long productId, String username) {
        User user = userService.findByName(username);
        Bucket bucket = user.getBucket();
        bucketService.addProducts(bucket, Collections.singletonList(productId));
    }

    public void createOrder(User user){
        Order order = new Order();
        order.setCreated(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);
        order.setUser(user);
        orderService.saveOrder(order);
    }

    @Override
    @Transactional
    public void addToUserOrder(Long productId, String username) {
        User user = userService.findByName(username);
        Bucket bucket = user.getBucket();
        if (user.getOrders().size() == 0){
            createOrder(user);
        }
        else {
            List<Order> orders = user.getOrders();
            Order order = user.getOrders().get(orders.size() - 1);
            orderService.addProducts(order);
            //bucketService.deleteProducts(bucket, Collections.singletonList(productId));
        }
    }
    @Override
    @Transactional
    public void deleteFromUserBucket(Long productId, String username) {
        User user = userService.findByName(username);
        if (user == null) throw new RuntimeException("Пользователь не найден");
        Bucket bucket = user.getBucket();
        bucketService.deleteProducts(bucket, Collections.singletonList(productId));
    }

    @Override
    @Transactional
    public void deleteFromUserOrder(Long productId, String username) {
        User user = userService.findByName(username);
        if (user == null) throw new RuntimeException("Пользователь не найден");
        List<Order> orders = user.getOrders();
        Order order = user.getOrders().get(orders.size() - 1);
     //   orderService.deleteProducts(order, Collections.singletonList(productId), user);
    }




    @Override
    @Transactional
    public void addProduct(ProductDTO dto) {
        Product product = mapper.toProduct(dto);
        Product savedProduct = productRepository.save(product);

        template.convertAndSend("/topic/products",
                ProductMapper.MAPPER.fromProduct(savedProduct));
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