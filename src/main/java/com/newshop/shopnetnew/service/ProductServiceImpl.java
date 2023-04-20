package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.dao.ProductRepository;
import com.newshop.shopnetnew.domain.Bucket;
import com.newshop.shopnetnew.domain.Product;
import com.newshop.shopnetnew.domain.User;
import com.newshop.shopnetnew.dto.ProductDTO;
import com.newshop.shopnetnew.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.PageAdapter;
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
    private final SimpMessagingTemplate template;
    public ProductServiceImpl(ProductRepository productRepository, UserService userService, BucketService bucketService, SimpMessagingTemplate template) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
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