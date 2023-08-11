package com.example.authfirebase.Service.ipml;

import com.example.authfirebase.DTO.Request.LoginRequestDTO;
import com.example.authfirebase.DTO.Response.ResponseObjectDTO;
import com.example.authfirebase.DTO.Response.UserResponseDTO;
import com.example.authfirebase.Repository.UserRepository;
import com.example.authfirebase.Service.AuthService;
import com.example.authfirebase.model.Roles;
import com.example.authfirebase.model.Users;
import com.example.authfirebase.securitty.JwtTokenProvider;
import com.example.authfirebase.securitty.MyUserDetails;
import com.example.authfirebase.securitty.oauth2.OAuth2UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class AuthServiceIml implements AuthService {
    private JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MyUserDetails myUserDetails;

    @Override
    public ResponseObjectDTO signIn(LoginRequestDTO userRequest) {
        try {
            String username = userRequest.getUserName();
            String password = userRequest.getPassWordUser();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserDetails userDetails = myUserDetails.loadUserByUsername(username);
            String token = jwtTokenProvider.CreateToken(userDetails);
            UserResponseDTO user = new UserResponseDTO();
            List<Users> users = userRepository.findUsersByNameUser(username);
            if (users.isEmpty()) return new ResponseObjectDTO("404", "User not found ", null);
            user.setUserName(users.get(0).getNameUser());
            user.setEmail(users.get(0).getEmail());
            user.setRole(users.get(0).getRole());
            user.setToken(token);
            return new ResponseObjectDTO("200", "Signin successfully ", user);
        } catch (AuthenticationException e) {
            return new ResponseObjectDTO("500", "Signin fail, name or pass is wrong ", null);

        }
    }

    @Override
    public ResponseObjectDTO signUp(Users appUser) {
        String name = appUser.getNameUser();
        String pass = appUser.getPassWordUser();
        if (checkExitsUser(name, pass)) {
            appUser.setPassWordUser(passwordEncoder.encode(pass));
            appUser.setRole(Roles.ROLE_CLIENT.toString());
            appUser.setIsDelete(false);
            userRepository.save(appUser);
            UserResponseDTO user = new UserResponseDTO();
            user.setUserName(appUser.getNameUser());
            user.setEmail(appUser.getEmail());
            user.setRole(appUser.getRole());
            user.setToken("");
            return new ResponseObjectDTO("200", "SignUp successfully ", user);
        } else {
            return new ResponseObjectDTO("500", "SignUp fail ", null);
        }
    }

    @Override
    public Users saveUserOauth2(OAuth2UserInfo oAuth2UserInfo) {
        Users users = new Users();
        users.setIsDelete(false);
//        users.setFirstName(oAuth2UserInfo.getFirstName());
//        users.setLastName(oAuth2UserInfo.getLastName());
        users.setRole(Roles.ROLE_CLIENT.toString());
        users.setNameUser("google");
        users.setEmail(oAuth2UserInfo.getEmail());
        users.setPassWordUser("Oauth2");
        userRepository.save(users);
        System.out.println("save successfully user login oauth2");
        return users;

    }


    @Override
    public boolean checkExitsUser(String userName, String email) {
        return userRepository.findByEmail(email).isEmpty() && userRepository.findUsersByNameUser(userName).isEmpty();
    }

}
