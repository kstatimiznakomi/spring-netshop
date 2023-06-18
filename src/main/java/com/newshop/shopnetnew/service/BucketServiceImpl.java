package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.dao.BucketRepository;
import com.newshop.shopnetnew.dao.ProductRepository;
import com.newshop.shopnetnew.domain.*;
import com.newshop.shopnetnew.dto.BucketDTO;
import com.newshop.shopnetnew.dto.BucketDetailDTO;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService {
    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final UserService userService;
    public BucketServiceImpl(@Lazy BucketRepository bucketRepository, @Lazy ProductRepository productRepository, @Lazy UserService userService, @Lazy BucketService bucketService, OrderService orderService){
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.orderService = orderService;
    }

   /* @Override
    public Bucket addToUserBucket(User user, List<Long> productIds, Bucket bucket) {
        bucket.setUser(user);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        bucket.setProducts(productList);
        return bucketRepository.save(bucket);
    }*/

    private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
        return productIds.stream()
                .map(productRepository::getOne)
                .collect(Collectors.toList());
    }

    //IMPORTANT FOR SALES
    @Override
    public void addProducts(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectRefProductsByIds(productIds));
        bucket.setProducts(newProductList);
        bucketRepository.save(bucket);
    }

    @Override
    public void save(Bucket bucket){
        bucketRepository.save(bucket);
    }

    @Override
    public void deleteProducts(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        products.removeAll(getCollectRefProductsByIds(productIds));
        bucket.setProducts(products);
        bucketRepository.save(bucket);
    }

    @Override
    public BucketDTO getBucketByUser(String name) {
        User user = userService.findByName(name);
        Bucket bucket = bucketRepository.getBucketByUser(user);
        if (user == null || bucket == null){
            return new BucketDTO();
        }
        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, BucketDetailDTO> mapByProductId = new HashMap<>();

        List<Product> products = bucket.getProducts();
        for (Product product : products){
            BucketDetailDTO detail = mapByProductId.get(product.getId());
            if(detail == null){
                mapByProductId.put(product.getId(), new BucketDetailDTO(product));
            } else {
                detail.setAmount(detail.getAmount().add(new BigDecimal(1.0)));
                detail.setSum(detail.getSum() + Double.valueOf(product.getPrice().toString()));
            }
        }
        bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDTO.aggregate();
        return bucketDTO;
    }

    @Override
    public Bucket getBucketByUser(User user) {
        return bucketRepository.getBucketByUser(user);
    }

    @Override
    @Transactional
    public void commitAllBucketToOrder(String username) {
        User user = userService.findByName(username);
        if(user == null){
            throw new RuntimeException("User is not found");
        }
        Bucket bucket = bucketRepository.getBucketByUser(user);
        if(bucket == null || bucket.getProducts().isEmpty()){
            return;
        }

        Order order = orderService.getOrderByStatusAndUser(OrderStatus.NEW, user);

        Map<Product, Long> productWithAmount = bucket.getProducts().stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        List<OrderDetails> orderDetails = productWithAmount.entrySet().stream()
                .map(pair -> new OrderDetails(order, pair.getKey(), pair.getValue()))
                .collect(Collectors.toList());

        BigDecimal total = new BigDecimal(orderDetails.stream()
                .map(detail -> detail.getPrice().multiply(detail.getAmount()))
                .mapToDouble(BigDecimal::doubleValue).sum());

        order.setDetails(orderDetails);
        order.setSum(total);

        orderService.saveOrder(order);
        bucket.getProducts().clear();
        bucketRepository.save(bucket);
    }
    @Override
    @Transactional
    public void commitOneBucketToOrder(String username, Long id) {
        User user = userService.findByName(username);
        if(user == null){
            throw new RuntimeException("User is not found");
        }
        Bucket bucket = bucketRepository.getBucketByUser(user);
        if(bucket == null || bucket.getProducts().isEmpty()){
            return;
        }
        Product product = productRepository.getProductById(id);
        Order order = orderService.getOrderByStatusAndUser(OrderStatus.NEW, user);

        orderService.addProducts(order, Collections.singletonList(id));

        BigDecimal total = new BigDecimal(order.getDetails().stream()
                .map(detail -> detail.getPrice().multiply(detail.getAmount()))
                .mapToDouble(BigDecimal::doubleValue).sum());

        order.setDetails(order.getDetails());
        order.setSum(total);

        orderService.saveOrder(order);
        bucket.getProducts().remove(product);
        bucketRepository.save(bucket);
    }
}
