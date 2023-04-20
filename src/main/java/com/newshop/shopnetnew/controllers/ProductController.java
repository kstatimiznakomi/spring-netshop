package com.newshop.shopnetnew.controllers;

import com.newshop.shopnetnew.dao.ProductRepository;
import com.newshop.shopnetnew.domain.Product;
import com.newshop.shopnetnew.dto.ProductDTO;
import com.newshop.shopnetnew.service.ProductService;
import com.newshop.shopnetnew.service.SessionObjectHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final SessionObjectHolder sessionObjectHolder;

    public ProductController(ProductService productService, SessionObjectHolder sessionObjectHolder) {
        this.productService = productService;
        this.sessionObjectHolder = sessionObjectHolder;
    }

    @GetMapping
    public String list(Model model){
        sessionObjectHolder.addClick();
        List<ProductDTO> list = productService.getAll();
        model.addAttribute("products", list);
        return "products";
    }

    @GetMapping("?p={pageNumber}")
    public String listProductsOther(Model model, @PathVariable("pageNumber") int pageNumber){
        Page<Product> page = productService.getAllPage(pageNumber);
        int totalPages = page.getTotalPages();
        if (pageNumber < 1) {
            return "redirect:/products?p=1";
        }
        if (pageNumber > totalPages) {
            return "redirect:/products?p=" + totalPages;
        }

        return listPage(model, pageNumber);
    }

    public String listPage(Model model, int pageNumber){
        Page<Product> page = productService.getAllPage(pageNumber);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<Product> listProducts = page.getContent();
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listProducts", listProducts);
        return "products";
    }

    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal){
        sessionObjectHolder.addClick();
        if(principal == null){
            return "redirect:/products";
        }
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/products";
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(ProductDTO dto){
        productService.addProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @MessageMapping("/products")
    public void messageAddProduct(ProductDTO dto){
        productService.addProduct(dto);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ProductDTO getById(@PathVariable Long id){
        return productService.getById(id);
    }

}
