package com.newshop.shopnetnew.controllers;

import com.newshop.shopnetnew.dao.OrderRepository;
import com.newshop.shopnetnew.domain.DeliveryPoints;
import com.newshop.shopnetnew.domain.Order;
import com.newshop.shopnetnew.domain.OrderStatus;
import com.newshop.shopnetnew.domain.User;
import com.newshop.shopnetnew.dto.CategoryDTO;
import com.newshop.shopnetnew.dto.OrderDTO;
import com.newshop.shopnetnew.dto.PointDTO;
import com.newshop.shopnetnew.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final DeliveryPointsService deliveryPointsService;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final PDFGeneratorService pdfGeneratorService;

    public OrderController(OrderService orderService, OrderRepository orderRepository, ProductService productService, DeliveryPointsService deliveryPointsService, UserService userService, PDFGeneratorService pdfGeneratorService) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.deliveryPointsService = deliveryPointsService;
        this.userService = userService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @GetMapping("/close/{order_id}")
    public String closeOrder(Model model, @PathVariable("order_id") Long order_id) {
        Order order = orderRepository.getOrderById(order_id);
        if (order.getStatus() == OrderStatus.COMPLETE || order.getStatus() == OrderStatus.CANCELLED){
            return "redirect:/orders/" + order_id;
        }
        order.setStatus(OrderStatus.GOING);
        orderRepository.save(order);
        return "redirect:/orders/" + order_id;
    }

    @GetMapping("/get")
    public String going(Principal principal, @RequestParam Long order, @RequestParam Long point) {
        if (principal == null){
            return "redirect:/products";
        }
        User user = userService.findByName(principal.getName());
        List<Order> orders = user.getOrders();
        Order orderr = orderRepository.getOrderById(order);
        boolean exist = false;
        if (orderr == null){
            Order existOrder = orderService.getOrderByStatusAndUser(OrderStatus.NEW, user);
            return "redirect:/orders/" + existOrder.getId();
        }
        if (orderr.getStatus() == OrderStatus.NEW) {
            exist = true;
        }
        if(exist){
            DeliveryPoints pointt = deliveryPointsService.getPointById(point);
            orderr.setStatus(OrderStatus.GOING);
            orderService.saveOrder(orderr);
            deliveryPointsService.addOrders(pointt, Collections.singletonList(order));
            return "redirect:/orders/" + order;
        }
        Order newOrder = orderService.getOrderByStatusAndUser(OrderStatus.NEW, user);
        return "redirect:/orders/" + newOrder.getId();
    }



    @GetMapping("/point")
    public String byPoint(Model model){
       List<Order> orders = orderService.getOrdersByPoint(1L);
        model.addAttribute("orders", orders);
        model.addAttribute("orderStatusNew", OrderStatus.NEW);
        model.addAttribute("orderStatusComplete", OrderStatus.COMPLETE);
        model.addAttribute("orderStatusCancelled", OrderStatus.CANCELLED);
       return "orders-point";
    }

    @GetMapping("/cancel/{order_id}")
    public String cancelOrder(Model model, @PathVariable("order_id") Long order_id) {
        Order order = orderRepository.getOrderById(order_id);
        if (order.getStatus() == OrderStatus.COMPLETE || order.getStatus() == OrderStatus.CANCELLED){
            return "redirect:/orders";
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/pdf/{orderId}")
    void exportPdf(HttpServletResponse response, @PathVariable("orderId") Long orderId) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=check_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.pdfGeneratorService.export(response, orderId);
    }

    @GetMapping("/{order_id}")
    //@ResponseBody
    public String getOrderDetailsByOrder(Model model,
                                         @PathVariable("order_id") Long order_id,
                                         Principal principal){
        if (principal == null) {
            return "redirect:/products";
        }
        Order order = orderRepository.getOrderById(order_id);
        if(order == null){
            return "redirect:/orders";
        }
        User user = userService.findByName(principal.getName());
        List<Order> ordersByUser = user.getOrders();
        boolean exist = false;
        for (Order orders : ordersByUser){
            if (order.getId() == orders.getId()) {
                exist = true;
                break;
            }
        }
        if(exist) {
            OrderDTO orderDetailsDTOS = orderService.getOrderByUser(order.getId());
            List<PointDTO> points = deliveryPointsService.getAll();
            model.addAttribute("points", points);
            model.addAttribute("order_id", order_id);
            model.addAttribute("order", order);
            model.addAttribute("orderStatusNew", OrderStatus.NEW);
            model.addAttribute("orderStatusComplete", OrderStatus.COMPLETE);
            model.addAttribute("orderStatusCancelled", OrderStatus.CANCELLED);
            model.addAttribute("orderDetails", orderDetailsDTOS.getDetails());
            return "detailsPage";
        }
        else return "redirect:/orders";
    }

    @GetMapping("/{order_id}/go/{pointId}")
    public String go(Model model,
                     @PathVariable("order_id") Long order_id,
                     @PathVariable("pointId") Long pointId,
                     Principal principal){
        if (principal == null) {
            return "redirect:/products";
        }
        Order order = orderRepository.getOrderById(order_id);
        if(order == null){
            return "redirect:/orders";
        }
        User user = userService.findByName(principal.getName());
        List<Order> ordersByUser = user.getOrders();
        boolean exist = false;
        for (Order orders : ordersByUser){
            if (order.getId() == orders.getId()){
                exist = true;
            }
        }
        if(exist) {
            order.setStatus(OrderStatus.GOING);
            orderRepository.save(order);
            return "detailsPage";
        }
        else return "redirect:/orders";
    }

    @GetMapping
    public String getOrders(Model model, Principal principal){
        if (principal == null){
            return "order-empty";
        }
        else {
            List<Order> orders = orderService.getOrdersByUser(principal.getName());
            model.addAttribute("orders", orders);
            model.addAttribute("principal", principal);
            model.addAttribute("orderStatusNew", OrderStatus.NEW);
            model.addAttribute("orderStatusGoing", OrderStatus.GOING);
            model.addAttribute("orderStatusDelivered", OrderStatus.DELIVERED);
            model.addAttribute("orderStatusComplete", OrderStatus.COMPLETE);
            model.addAttribute("orderStatusCancelled", OrderStatus.CANCELLED);
        }
        return "orders";
    }
}
