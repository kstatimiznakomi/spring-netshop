package com.newshop.shopnetnew.controllers;


import com.newshop.shopnetnew.dao.UserRepository;
import com.newshop.shopnetnew.domain.Role;
import com.newshop.shopnetnew.domain.User;
import com.newshop.shopnetnew.dto.UserDTO;
import com.newshop.shopnetnew.service.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Principal principal, Model model) {
        if(principal == null){
            return "/products";
        }
        User user = userService.findByName(principal.getName());
        if (user.getRole() != Role.ADMIN){
            return "/products";
        }
        model.addAttribute("users", userService.getAll());
        return "users";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit")
    public String toUserRoleEdit(Principal principal, Model model){
        if (principal == null){
            return "redirect:/products";
        }
        User user = userService.findByName(principal.getName());
        if (user.getRole() != Role.ADMIN){
            return "redirect:/products";
        }
        model.addAttribute("users", userService.getAll());
        model.addAttribute("roleAdmin", Role.ADMIN);
        model.addAttribute("roleClient", Role.CLIENT);
        return "users-edit";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/edit/complete")
    public String userRoleEdit(@RequestParam Long id, @RequestParam String role, Model model){
        User user = userRepository.getUserById(id);
        if(user == null){
            return "redirect:/users/edit";
        }
        if(Objects.equals(role, String.valueOf(Role.ADMIN))){
            user.setRole(Role.ADMIN);
        }
        if(Objects.equals(role, String.valueOf(Role.CLIENT))){
            user.setRole(Role.CLIENT);
        }
        userService.save(user);
        model.addAttribute("users", userService.getAll());
        model.addAttribute("roleAdmin", Role.ADMIN);
        model.addAttribute("roleClient", Role.CLIENT);
        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String newUser(Model model){
        System.out.println("Called method newUser");
        model.addAttribute("user", new UserDTO());
        return "user";
    }
    @PostAuthorize("isAuthenticated() and #username == authentication.principal.username")
    @GetMapping("/{name}/roles")
    @ResponseBody
    public String getRoles(@PathVariable("name") String username){
        System.out.println("called method getRoles");
        User byName = userService.findByName(username);
        return byName.getRole().name();
    }

    @PostMapping("/new")
    public String saveUser(UserDTO dto, Model model){
        if(userService.saveFromAdmin(dto)){
            return "redirect:/users";
        }
        else {
            model.addAttribute("user", dto);
            return "user";
        }
    }
}
