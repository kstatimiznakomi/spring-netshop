package com.newshop.shopnetnew.dao;

import com.newshop.shopnetnew.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByUsername(String name);
    Optional<User> findFirstByEmail(String email);
}
