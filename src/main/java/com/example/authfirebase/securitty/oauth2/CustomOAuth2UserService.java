package com.example.authfirebase.securitty.oauth2;

import com.example.authfirebase.Repository.UserRepository;
import com.example.authfirebase.Service.AuthService;
import com.example.authfirebase.Service.UserService;
import com.example.authfirebase.model.Users;

import com.example.authfirebase.securitty.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {


    private final AuthService userService;
    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
       // String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserFactory.getOAuth2UserInfo(oAuth2User.getAttributes());
        List<Users> user = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        if (user.isEmpty()) {
            user.add(userService.saveUserOauth2(oAuth2UserInfo));}
        return UserPrincipal.create(user.get(0), oAuth2User.getAttributes());
    }
}
