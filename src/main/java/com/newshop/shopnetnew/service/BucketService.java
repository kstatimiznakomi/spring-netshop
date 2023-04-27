package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.domain.Bucket;
import com.newshop.shopnetnew.domain.User;
import com.newshop.shopnetnew.dto.BucketDTO;
import jakarta.transaction.Transactional;

import java.security.Principal;
import java.util.List;

public interface BucketService {
    Bucket createBucket(User user);
    void addProducts(Bucket bucket, List<Long> productIds);
    BucketDTO getBucketByUser(String name);
    Bucket getBucketByUser(User user);
    void deleteProducts(Bucket bucket, List<Long> productIds);

    @Transactional
    void commitBucketToOrder(String username);
}
