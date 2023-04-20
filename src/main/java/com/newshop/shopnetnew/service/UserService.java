package com.newshop.shopnetnew.service;

import com.newshop.shopnetnew.domain.Order;
import com.newshop.shopnetnew.domain.User;
import com.newshop.shopnetnew.dto.UserDTO;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    boolean save(UserDTO userDTO);
    boolean saveFromAdmin(UserDTO userDTO);
    Order getOrderByUser(User user);
    void save(User user);
    boolean exist(UserDTO userDTO);
   List<UserDTO> getAll();
   User findByName(String name);
   void updateProfile(UserDTO userDTO);

    String signUpUser(User user);
}
