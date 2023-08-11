package com.example.authfirebase.Service;

import com.example.authfirebase.DTO.Request.LoginRequestDTO;
import com.example.authfirebase.DTO.Response.ResponseObjectDTO;
import com.example.authfirebase.model.Users;
import com.example.authfirebase.securitty.oauth2.OAuth2UserInfo;

public interface AuthService {
    ResponseObjectDTO signIn(LoginRequestDTO loginRequest);
    ResponseObjectDTO signUp(Users users);
    Users saveUserOauth2(OAuth2UserInfo oAuth2UserInfo);
    boolean checkExitsUser(String userName , String email);

}
