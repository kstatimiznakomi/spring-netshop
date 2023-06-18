package com.newshop.shopnetnew.controllers;

import com.newshop.shopnetnew.domain.Product;
import com.newshop.shopnetnew.dto.BrandDTO;
import com.newshop.shopnetnew.dto.CategoryDTO;
import com.newshop.shopnetnew.service.BrandService;
import com.newshop.shopnetnew.service.CategoryService;
import com.newshop.shopnetnew.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class SearchController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @RequestMapping("/search")
    public String toProductsWord(@RequestParam String q,
                                 @RequestParam Long cat,
                                 @RequestParam Long br, Model model){
        if (!Objects.equals(q, "") && cat == 0L && br == 0L) return byName(q, model);
        if (Objects.equals(q, "") && cat != 0L && br == 0L) return byCategory(cat, model);
        if (Objects.equals(q, "") && cat == 0L && br != 0L) return byBrand(cat, model);
        if (!Objects.equals(q, "") && cat != 0L && br == 0L) return byWordCat(q, cat, model);
       /* if (!Objects.equals(q, "") && cat == 0L && br != 0L) return byWordBr(cat, model);
        if (!Objects.equals(q, "") && cat != 0L && br != 0L) return byAll(cat, model);
        if (Objects.equals(q, "") && cat != 0L && br != 0L) return byCatBr(cat, model);*/


        return "redirect:/products";
    }

    public String byName(String name, Model model){
        Page<Product> page = productService.getProductsByName(name, 1);
        List<CategoryDTO> categories = categoryService.getAll();
        List<BrandDTO> brands = brandService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        long totalItems = page.getTotalElements();
        long totalPages = page.getTotalPages();
        List<Product> listProducts = page.getContent();
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("search", name);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listProducts", listProducts);
        return "search";
    }

    public String byWordCat(String name, Long catId, Model model){
        Page<Product> page = productService.getProductsByName(name, 1);
        List<CategoryDTO> categories = categoryService.getAll();
        List<BrandDTO> brands = brandService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        long totalItems = page.getTotalElements();
        long totalPages = page.getTotalPages();
        List<Product> listProducts = page.getContent();
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("search", name);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listProducts", listProducts);
        return "search";
    }

    private String byBrand(Long br, Model model) {
        Page<Product> page = productService.getProductsByBrand(br, 1);
        List<CategoryDTO> categories = categoryService.getAll();
        List<BrandDTO> brands = brandService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        long totalItems = page.getTotalElements();
        long totalPages = page.getTotalPages();
        List<Product> listProducts = page.getContent();
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("search", null);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listProducts", listProducts);
        return "search";
    }

    public String byCategory(Long catId, Model model){
        Page<Product> page = productService.getProductsByCategory(catId, 1);
        List<CategoryDTO> categories = categoryService.getAll();
        List<BrandDTO> brands = brandService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        long totalItems = page.getTotalElements();
        long totalPages = page.getTotalPages();
        List<Product> listProducts = page.getContent();
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("search", null);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listProducts", listProducts);
        return "search";
    }
}
