package com.example.authfirebase.securitty.oauth2;

import com.example.authfirebase.Service.UserService;
import com.example.authfirebase.model.Users;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {


    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserFactory.getOAuth2UserInfo(provider, oAuth2User.getAttributes());
        List<Users> user = userService.findByEmail(oAuth2UserInfo.getEmail());

        if (user == null) {
            Users users = userService.saveUserOauth2(oAuth2UserInfo);}
//        } else {
//            user = authenticationService.updateOauth2User(user, provider, oAuth2UserInfo);
//        }
        return U.create(users, oAuth2User.getAttributes());
    }
}
