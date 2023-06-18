package com.newshop.shopnetnew.controllers;

import com.newshop.shopnetnew.dto.BrandDTO;
import com.newshop.shopnetnew.dto.CategoryDTO;
import com.newshop.shopnetnew.service.BrandService;
import com.newshop.shopnetnew.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping({"", "/"})
public class MainController {
    private final CategoryService categoryService;
    private final BrandService brandService;
    public MainController(CategoryService categoryService, BrandService brandService) {
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping("/")
    public String index(Model model){
        List<CategoryDTO> categories = categoryService.getAll();
        List<BrandDTO> brands = brandService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
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
