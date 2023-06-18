package com.newshop.shopnetnew.controllers;

import com.newshop.shopnetnew.domain.Order;
import com.newshop.shopnetnew.domain.OrderStatus;
import com.newshop.shopnetnew.domain.User;
import com.newshop.shopnetnew.dto.BucketDTO;
import com.newshop.shopnetnew.dto.ProductDTO;
import com.newshop.shopnetnew.service.*;
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
    private final OrderService orderService;
    private final UserService userService;

    public BucketController(BucketService bucketService, ProductService productService, OrderService orderService, UserService userService) {
        this.bucketService = bucketService;
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
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
    @GetMapping("/order/all")
    public String addAllOrder(Principal principal){
        if(principal == null){
            return "redirect:/products";
        }
        User user = userService.findByName(principal.getName());
        Order order = orderService.getOrderByStatusAndUser(OrderStatus.NEW, user);
        if (order == null){
            orderService.createOrder(user);
            bucketService.commitAllBucketToOrder(principal.getName());
            return "redirect:/bucket";
        }
        else {
            bucketService.commitAllBucketToOrder(principal.getName());
            return "redirect:/bucket";
        }
    }

    @GetMapping("/{id}/order")
    public String addOneToOrder(@PathVariable Long id, Principal principal){
        if(principal == null){
            return "redirect:/products";
        }
        User user = userService.findByName(principal.getName());
        Order order = orderService.getOrderByStatusAndUser(OrderStatus.NEW, user);
        if (order == null){
            orderService.createOrder(user);
            bucketService.commitOneBucketToOrder(principal.getName(), id);
            return "redirect:/bucket";
        }
        else {
            bucketService.commitOneBucketToOrder(principal.getName(), id);
            return "redirect:/bucket";
        }

    }

    @GetMapping("/{productId}/delete")
    public String deleteBucket(@PathVariable Long productId, Principal principal){
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
