package com.newshop.shopnetnew.controllers;

import com.newshop.shopnetnew.dto.UserDTO;
import com.newshop.shopnetnew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping({"", "/"})
public class MainController {
    private final UserService userService;
    public MainController(UserService userService) {
        this.userService = userService;
    }

    public String index(){
        return "index";
    }
    @RequestMapping("/login")
    public String login(Principal principal){
        if(principal != null){
            return "redirect:products";
        }
        return "login";
    }


    @RequestMapping("/login-error")
    public String loginError(Model model) {
    	model.addAttribute("login-error", true);
    	return "login";
    }
}
