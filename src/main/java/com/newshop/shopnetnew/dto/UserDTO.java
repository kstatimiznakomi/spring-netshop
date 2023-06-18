package com.newshop.shopnetnew.dto;

import com.newshop.shopnetnew.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String personName;
    private String password;
    private String matchingPassword;
    private String email;
    private Role role;
}
