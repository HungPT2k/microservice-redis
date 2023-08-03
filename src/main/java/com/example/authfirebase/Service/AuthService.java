package com.example.authfirebase.Service;

import com.example.authfirebase.DTO.Request.LoginDTO;
import com.example.authfirebase.DTO.Response.ResponseObject;
import com.example.authfirebase.model.Users;
import com.example.authfirebase.securitty.oauth2.OAuth2UserInfo;

import java.util.List;

public interface AuthService {
    ResponseObject signIn(LoginDTO loginRequest);
    ResponseObject signUp(Users users);
    Users saveUserOauth2(OAuth2UserInfo oAuth2UserInfo);
    boolean checkExitsUser(String userName , String email);

}
