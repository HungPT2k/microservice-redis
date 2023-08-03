package com.example.authfirebase.Service;

import com.example.authfirebase.DTO.Request.LoginDTO;
import com.example.authfirebase.DTO.Response.ResponseObject;
import com.example.authfirebase.DTO.Request.RoleToUser;
import com.example.authfirebase.model.Users;
import com.example.authfirebase.securitty.oauth2.OAuth2UserInfo;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<Users> getAllUser();
    Optional<Users> getById(Long id);
    ResponseObject updateUser(Users newUser,Long id);
    ResponseObject deleteUser(Long id);
    UserDetails convertUserToUserDetail(Users users);
    public ResponseObject addRoleForUser(RoleToUser roleToUser);


}
