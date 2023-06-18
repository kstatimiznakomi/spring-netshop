package com.newshop.shopnetnew.controllers;

import com.newshop.shopnetnew.domain.User;
import com.newshop.shopnetnew.dto.UserDTO;
import com.newshop.shopnetnew.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public String profileUser(Model model, Principal principal){
        if(principal == null){
            throw new RuntimeException("You are not authorize");
        }
        User user = userService.findByName(principal.getName());

        UserDTO dto = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .personName(user.getPersonName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
        model.addAttribute("user", dto);
        return "profile";
    }

    @PostMapping("/edit")
    public String updateProfileUser(UserDTO dto, Model model, Principal principal){
        if (principal == null || !Objects.equals(principal.getName(), dto.getUsername())){
            return "redirect:/login";
        }
        if (!dto.getPassword().isEmpty()
                && !Objects.equals(dto.getPassword(), dto.getMatchingPassword())){
            model.addAttribute("user", dto);
            return "redirect:/profile";
        }
        userService.updateProfile(dto);
        return "redirect:/profile";
    }
}
