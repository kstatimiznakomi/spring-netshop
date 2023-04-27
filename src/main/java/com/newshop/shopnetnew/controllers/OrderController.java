package com.newshop.shopnetnew.controllers;

import com.newshop.shopnetnew.dao.OrderRepository;
import com.newshop.shopnetnew.domain.Order;
import com.newshop.shopnetnew.dto.OrderDTO;
import com.newshop.shopnetnew.service.OrderService;
import com.newshop.shopnetnew.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderController(OrderService orderService, OrderRepository orderRepository, ProductService productService) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{order_id}")
    //@ResponseBody
    public String getOrderDetailsByOrder(Model model, @PathVariable("order_id") Long order_id){
        Order order = orderRepository.getOrderById(order_id);
        OrderDTO orderDetailsDTOS = orderService.getOrderByUser(order.getId());
        model.addAttribute("orderDetails", orderDetailsDTOS.getDetails());
        return "detailsPage";
    }

    @GetMapping
    public String getOrders(Model model, Principal principal){
        if (principal == null){
            return "order-empty";
        }
        else {
            List<Order> orders = orderService.getOrdersByUser(principal.getName());
            model.addAttribute("orders", orders);
        }
        return "orders";
    }

}
