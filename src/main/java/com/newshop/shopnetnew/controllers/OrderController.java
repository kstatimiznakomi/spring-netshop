package com.newshop.shopnetnew.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/order")
public class OrderController {
    @GetMapping("")
    public String getOrderPage(Model model, Principal principal){
        if(principal == null){
            return "products";
        }
        return "order";
    }


}
