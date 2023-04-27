package com.newshop.shopnetnew.controllers;

import com.newshop.shopnetnew.domain.Product;
import com.newshop.shopnetnew.dto.ProductDTO;
import com.newshop.shopnetnew.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
@AllArgsConstructor
public class SearchController {
    private final ProductService productService;
    @PostMapping("{keyword}")
    public String searchByKeyword(@PathVariable("keyword") String keyword, Model model){
        model.addAttribute("query", new ProductDTO());
        return "products";
    }
}
