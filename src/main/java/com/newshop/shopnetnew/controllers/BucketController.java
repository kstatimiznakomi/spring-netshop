package com.newshop.shopnetnew.controllers;

import com.newshop.shopnetnew.dto.BucketDTO;
import com.newshop.shopnetnew.dto.ProductDTO;
import com.newshop.shopnetnew.service.BucketService;
import com.newshop.shopnetnew.service.ProductService;
import com.newshop.shopnetnew.service.SessionObjectHolder;
import com.newshop.shopnetnew.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/bucket")
public class BucketController {
    private final BucketService bucketService;
    private final ProductService productService;
    private final UserService userService;
    private final SessionObjectHolder sessionObjectHolder;

    public BucketController(BucketService bucketService, ProductService productService, UserService userService, SessionObjectHolder sessionObjectHolder) {
        this.bucketService = bucketService;
        this.productService = productService;
        this.userService = userService;
        this.sessionObjectHolder = sessionObjectHolder;
    }
    @GetMapping
    public String aboutBucket(Model model, Principal principal){
        if (principal == null){
            model.addAttribute("bucket", new BucketDTO());
            return "bucket";
        }
        else {
            BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucketDto", bucketDTO);
        }
        return "bucket";
    }
    @GetMapping("/{productId}/delete")
    public String deleteBucket(@PathVariable Long productId, Principal principal){
        sessionObjectHolder.addClick();
        if(principal == null){
            return "redirect:/products";
        }
        productService.deleteFromUserBucket(productId, principal.getName());
        return "redirect:/bucket";
    }
    @GetMapping("{id}")
    @ResponseBody
    public ProductDTO getById(@PathVariable Long id){
        return productService.getById(id);
    }
}
