package com.example.authfirebase.Service.ipml;

import com.example.authfirebase.DTO.LoginDTO;
import com.example.authfirebase.DTO.ResponseObject;
import com.example.authfirebase.DTO.RoleToUser;
import com.example.authfirebase.DTO.UserDTO;
import com.example.authfirebase.Repository.UserRepository;
import com.example.authfirebase.Service.UserService;
import com.example.authfirebase.exception.customException;
import com.example.authfirebase.model.Roles;
import com.example.authfirebase.model.Users;
import com.example.authfirebase.securitty.JwtTokenFilter;
import com.example.authfirebase.securitty.JwtTokenProvider;
import com.example.authfirebase.securitty.MyUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceIml implements UserService  {

    private JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MyUserDetails myUserDetails;


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
       Optional<Users> users= userRepository.findById(id);
       if (users.isEmpty()) return new ResponseObject("404","Not found",null);
       if(users.get().getRole().indexOf(Roles.ROLE_SUPERADMIN.toString())>0) return new ResponseObject("403","Not claim",null);
        users.get().setIsDelete(true);
        userRepository.save(users.get());
       return new ResponseObject("200","Delete user by id","");
        }

    @Override
    public ResponseObject SignIn(LoginDTO userRequest) {
        try {
            String username= userRequest.getUserName();
            String password=userRequest.getPassWordUser();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserDetails userDetails =myUserDetails.loadUserByUsername(username);
            String token= jwtTokenProvider.CreateToken(userDetails);
            UserDTO user = new UserDTO();
            user.setUserName(userRepository.findUsersByNameUser(username).get(0).getNameUser());
            user.setEmail(userRepository.findUsersByNameUser(username).get(0).getEmail());
            user.setRole(userRepository.findUsersByNameUser(username).get(0).getRole());
            user.setToken(token);
            return new ResponseObject("200","signin successfully ",user);
        } catch (AuthenticationException e) {
            return new ResponseObject("400","signin fail, name or pass is wrong ",null);

        }
    }
    @Override
    public ResponseObject SignUp(Users appUser) {
        String name=appUser.getNameUser();
        String pass=appUser.getPassWordUser();
        if (CheckExitsUser(name,pass)) {
            appUser.setPassWordUser(passwordEncoder.encode(pass));
            appUser.setRole(Roles.ROLE_CLIENT.toString());
            appUser.setIsDelete(false);
            userRepository.save(appUser);
            UserDTO user = new UserDTO();
            user.setUserName(appUser.getNameUser());
            user.setEmail(appUser.getEmail());
            user.setRole(appUser.getRole());
            user.setToken("");
            return new ResponseObject("200","SignUp successfully ",user);
        } else {
            return new ResponseObject("500","SignUp fail ",null);
        }
    }
    @Override
    public void SaveUserOauth2(String username,String email){
        Users users = new Users();
        users.setIsDelete(false);
        users.setRole(Roles.ROLE_CLIENT.toString());
        users.setNameUser(username);
        users.setEmail(email);
        users.setPassWordUser("Oauth2");
        userRepository.save(users);
        System.out.println("save successfully user login oauth2");

    }



    @Override
    public UserDetails ConvertUserToUserDetail(Users users) {
        if(users==null) {
            return null;
        }
        String[] roles = users.getRole().split("\\|");

        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (int i = 0; i < roles.length; i++) {
            grantedAuthorities.add(new SimpleGrantedAuthority(roles[i]));
        }
        return new User(users.getNameUser(),users.getPassWordUser(),grantedAuthorities);
    }



    @Override
    public boolean CheckExitsUser(String userName, String email) {
        return userRepository.findByEmail(email).isEmpty() && userRepository.findUsersByNameUser(userName).isEmpty();
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
