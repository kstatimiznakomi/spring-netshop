package com.newshop.shopnetnew.controllers;

import com.newshop.shopnetnew.dto.UserDTO;
import com.newshop.shopnetnew.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {
    private final UserService userService;

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
        if (userService.exist(dto)){
            throw new IllegalStateException("Пользователь уже существует");
        }
        else {
            userService.save(dto);
            model.addAttribute("user", dto);
            return "login";
        }
    }
}
