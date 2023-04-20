package com.newshop.shopnetnew.mapper;

import com.newshop.shopnetnew.domain.Category;
import com.newshop.shopnetnew.domain.Product;
import com.newshop.shopnetnew.domain.User;
import com.newshop.shopnetnew.dto.ProductDTO;
import com.newshop.shopnetnew.dto.UserDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
    User toUser(UserDTO dto);
    @InheritInverseConfiguration
    UserDTO fromUser(User user);
    List<User> toUserList(List<UserDTO> userDTOS);
    List<UserDTO> formUserList(List<User> products);
}
