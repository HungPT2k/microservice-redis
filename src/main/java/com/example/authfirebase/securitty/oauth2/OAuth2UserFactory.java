package com.example.authfirebase.securitty.oauth2;

import lombok.SneakyThrows;

import javax.naming.AuthenticationException;
import java.util.Map;

public class OAuth2UserFactory {

    @SneakyThrows
    public static OAuth2UserInfo getOAuth2UserInfo( Map<String, Object> attributes) {

            return new GoogleOAuth2UserInfo(attributes);

    }
}
