package com.newshop.shopnetnew.dao;

import com.newshop.shopnetnew.domain.Bucket;
import com.newshop.shopnetnew.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {
    public Bucket getBucketByUser(User user);
}
