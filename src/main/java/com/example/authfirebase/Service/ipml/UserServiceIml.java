package com.example.authfirebase.Service.ipml;

import com.example.authfirebase.DTO.Request.LoginDTO;
import com.example.authfirebase.DTO.Request.RoleToUser;
import com.example.authfirebase.DTO.Response.ResponseObject;
import com.example.authfirebase.DTO.Response.UserDTO;
import com.example.authfirebase.Repository.UserRepository;
import com.example.authfirebase.Service.UserService;
import com.example.authfirebase.model.Roles;
import com.example.authfirebase.model.Users;
import com.example.authfirebase.securitty.JwtTokenProvider;
import com.example.authfirebase.securitty.MyUserDetails;
import com.example.authfirebase.securitty.oauth2.OAuth2UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceIml implements UserService {
    private final UserRepository userRepository;



    @Override
    public List<Users> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public ResponseObject updateUser(Users newUser, Long id) {
        return null;
    }

    @Override
    public ResponseObject deleteUser(Long id) {
        Optional<Users> users = userRepository.findById(id);
        if (users.isEmpty()) return new ResponseObject("404", "Not found", null);
//       if(users.get().getRole().indexOf(Roles.ROLE_SUPERADMIN.toString())>0) return new ResponseObject("403","Not claim",null);
        if (users.get().getIsDelete()) {
            users.get().setIsDelete(false);
            userRepository.save(users.get());
            return new ResponseObject("200", "InActive user by id " + id, "");
        }
        users.get().setIsDelete(true);
        userRepository.save(users.get());
        return new ResponseObject("200", "Active user by id " + id, "");
    }






    @Override
    public UserDetails convertUserToUserDetail(Users users) {
        if (users == null) {
            return null;
        }
        String[] roles = users.getRole().split("\\|");

        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (int i = 0; i < roles.length; i++) {
            grantedAuthorities.add(new SimpleGrantedAuthority(roles[i]));
        }
        return new User(users.getNameUser(), users.getPassWordUser(), grantedAuthorities);
    }

    @Override
    public ResponseObject addRoleForUser(RoleToUser roleToUser) {
        System.out.println(roleToUser);
        String id = roleToUser.getIdUser();
        String IdRole = roleToUser.getIdRole();
        String role = null;
        switch (IdRole) {
            case "1" -> role = Roles.ROLE_CLIENT.toString();
            case "2" -> role = Roles.ROLE_ADMIN.toString();
            case "3" -> role = Roles.ROLE_SUPERADMIN.toString();
            default -> {
            }
        }

        Optional<Users> user = userRepository.findById(Long.valueOf(id));
        if (user.isEmpty() || role == null) return new ResponseObject("404", "User is not already ", "");
        String userRole = user.get().getRole();
        if (userRole.indexOf(role) == -1) {
            userRole = userRole + "|" + role;
            user.get().setRole(userRole);
            userRepository.save(user.get());
            return new ResponseObject("200", "User id " + id + " add role " + role + " successfully", user.get());
        }
        return new ResponseObject("400", "false " + role + " is already", user.get());

    }

}
