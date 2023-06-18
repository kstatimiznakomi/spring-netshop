package com.newshop.shopnetnew.controllers;

import com.newshop.shopnetnew.domain.Bucket;
import com.newshop.shopnetnew.domain.Role;
import com.newshop.shopnetnew.domain.User;
import com.newshop.shopnetnew.dto.UserDTO;
import com.newshop.shopnetnew.service.BucketService;
import com.newshop.shopnetnew.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final BucketService bucketService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String registration(Principal principal, Model model){
        if(principal != null){
            return "redirect:/products";
        }
        model.addAttribute("user", new UserDTO());
        return "registration";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        System.out.println("Called method newUser");
        model.addAttribute("user", new UserDTO());
        return "login";
    }

    @PostMapping("/new")
    public String registerNewUser(UserDTO dto, Model model){
        if (!Objects.equals(dto.getPassword(), dto.getMatchingPassword())){
            throw new RuntimeException("Неправильный пароль");
        }
        if (!userService.exist(dto)){
            User user = User.builder()
                    .username(dto.getUsername())
                    .personName(dto.getPersonName())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .email(dto.getEmail())
                    .role(Role.CLIENT)
                    .build();
            userService.save(user);
            Bucket bucket = new Bucket();
            bucket.setUser(user);
            bucketService.save(bucket);
            model.addAttribute("user", dto);
            return "login";
        }
        else {
            throw new IllegalStateException("Пользователь уже существует");
        }
    }
}
