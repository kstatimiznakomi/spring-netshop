package com.newshop.shopnetnew.endpoint;

import com.newshop.shopnetnew.dto.UserDTO;
import com.newshop.shopnetnew.service.UserService;
import com.newshop.shopnetnew.ws.Users.GetUsersRequest;
import com.newshop.shopnetnew.ws.Users.GetUsersResponse;
import com.newshop.shopnetnew.ws.Users.UsersWS;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;

@Endpoint
public class UsersEndpoint {
    public static final String NAMESPACE_URL = "http://www.newnetshop.com/shopnetnew/ws/users";
    private UserService userService;

    public UsersEndpoint(UserService userService){
        this.userService = userService;
    }
    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getUsersRequest")
    @ResponsePayload
    public GetUsersResponse getUserWs(@RequestPayload GetUsersRequest request)
            throws DatatypeConfigurationException {
        GetUsersResponse response = new GetUsersResponse();
        userService.getAll()
                .forEach(dto -> response.getUsers().add(createUserWS(dto)));
        return response;
    }

    private UsersWS createUserWS(UserDTO dto) {
        UsersWS ws = new UsersWS();
        ws.setId(dto.getId());
        ws.setName(dto.getUsername());
        ws.setName(dto.getPersonName());
        ws.setPassword(dto.getPassword());
        ws.setEmail(dto.getEmail());
        ws.setRole(String.valueOf(dto.getRole()));
        return ws;
    }
}
