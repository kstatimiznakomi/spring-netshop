package com.newshop.shopnetnew.controllers;

import com.newshop.shopnetnew.domain.Product;
import com.newshop.shopnetnew.domain.Role;
import com.newshop.shopnetnew.domain.User;
import com.newshop.shopnetnew.dto.BrandDTO;
import com.newshop.shopnetnew.dto.CategoryDTO;
import com.newshop.shopnetnew.dto.ProductDTO;
import com.newshop.shopnetnew.service.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @GetMapping
    public String goToPage(){
        return "redirect:/products/page=1";
    }

    @GetMapping("/page={pageNumber}")
    public String listProductsOther(Principal principal, Model model, @PathVariable("pageNumber") int pageNumber){
        Page<Product> page = productService.getAllPage(pageNumber);
        int totalPages = page.getTotalPages();
        if (pageNumber < 1) {
            return "redirect:/products?p=1";
        }
        if (pageNumber > totalPages) {
            return "redirect:/products?p=" + totalPages;
        }
        if(principal == null){
            return listPage(model, pageNumber);
        }
        User user = userService.findByName(principal.getName());
        if (Objects.equals(user.getRole(), Role.ADMIN)){
            return listPageAdmin(model, pageNumber);
        }
        else {
            return listPage(model, pageNumber);
        }
    }

    public String listPageAdmin(Model model, int pageNumber){
        Page<Product> page = productService.getAllPage(pageNumber);
        List<CategoryDTO> categories = categoryService.getAll();
        List<BrandDTO> brands = brandService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);

        long totalItems = page.getTotalElements();
        long totalPages = page.getTotalPages();
        List<Product> listProducts = page.getContent();
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("search", null);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listProducts", listProducts);
        return "products-admin";
    }

    public String listPage(Model model, int pageNumber){
        Page<Product> page = productService.getAllPage(pageNumber);
        List<CategoryDTO> categories = categoryService.getAll();
        List<BrandDTO> brands = brandService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);

        long totalItems = page.getTotalElements();
        long totalPages = page.getTotalPages();
        List<Product> listProducts = page.getContent();
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("search", null);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listProducts", listProducts);
        return "products";
    }



    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal){
        if(principal == null){
            return "redirect:/products";
        }
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/products";
    }
    @GetMapping("/{id}/order")
    public String addOrder(@PathVariable Long id, Principal principal){
        if(principal == null){
            return "redirect:/products";
        }
        productService.addToUserOrder(id, principal.getName());
        return "redirect:/products";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam String name, @RequestParam Double price, @RequestParam String img){
        Product product = productService.getProductByName(name);
        if (product == null) {
            productService.addProduct(name, price, img);
            return "redirect:/products";
        }
        else throw new RuntimeException("Товар уже существует");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ProductDTO getById(@PathVariable Long id){
        return productService.getById(id);
    }

}
