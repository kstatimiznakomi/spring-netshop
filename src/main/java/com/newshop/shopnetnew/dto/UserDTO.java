package com.newshop.shopnetnew.dto;

import com.newshop.shopnetnew.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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

    public Role roleFromHtml(){
        Document document = Jsoup.parse("userList");
        if (document.getElementById("#roles").val() == String.valueOf(Role.CLIENT)){
            this.role = Role.CLIENT;
        }
        if (document.getElementById("#roles").val() == String.valueOf(Role.ADMIN)){
            this.role = Role.ADMIN;
        }
        return this.role;
    }
}
