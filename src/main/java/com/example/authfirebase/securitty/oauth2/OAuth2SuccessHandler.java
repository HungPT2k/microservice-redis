package com.example.authfirebase.securitty.oauth2;

import com.example.authfirebase.Repository.UserRepository;
import com.example.authfirebase.exception.customException;
import com.example.authfirebase.model.Users;
import com.example.authfirebase.securitty.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtProvider;
    private final UserRepository userRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");
        System.out.println(email);
        List<Users> users = userRepository.findByEmail(email);
        if (users.isEmpty()) throw new customException("email not found", HttpStatus.NOT_FOUND);
        UserDetails userDetails = convertUserToUserDetail(users.get(0));
        String token = jwtProvider.CreateToken(userDetails);
        System.out.println(token);
        String hostname = "localhost:8080";
        String uri = UriComponentsBuilder.fromUriString("http://localhost:8080/user/home")
                .queryParam("token", token)
                .build().toUriString();
        response.setHeader("token",token);
        response.addHeader("token",token);
        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    public UserDetails convertUserToUserDetail(Users users) {

        String[] roles = users.getRole().split("\\|");

        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (int i = 0; i < roles.length; i++) {
            grantedAuthorities.add(new SimpleGrantedAuthority(roles[i]));
        }
        return new User(users.getNameUser(), users.getPassWordUser(), grantedAuthorities);
    }
}
