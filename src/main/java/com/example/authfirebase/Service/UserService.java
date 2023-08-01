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
    ResponseObject signIn(LoginDTO loginRequest);
    ResponseObject signUp(Users users);
    UserDetails convertUserToUserDetail(Users users);
    boolean checkExitsUser(String userName , String email);
    public ResponseObject addRoleForUser(RoleToUser roleToUser);
    Users saveUserOauth2(OAuth2UserInfo oAuth2UserInfo);
    List<Users> findByEmail(String email);

}
