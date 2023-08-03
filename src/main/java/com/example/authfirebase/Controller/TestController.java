package com.example.authfirebase.Controller;

import com.example.authfirebase.DTO.Request.LoginDTO;
import com.example.authfirebase.DTO.Response.ResponseObject;
import com.example.authfirebase.DTO.Request.RoleToUser;
import com.example.authfirebase.Repository.UserRepository;
import com.example.authfirebase.Service.AuthService;
import com.example.authfirebase.Service.UserService;
import com.example.authfirebase.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/user")

public class TestController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @GetMapping("/hello")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public String test(){
        return "Hello User" ;
    };
    @GetMapping(path = "/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPERADMIN')")
    List<Users> getAllUser(){
        return userService.getAllUser();
    }
    @GetMapping("/deleteOK/{id}")
    void deleteOk(@PathVariable(value = "id") Long id){

        userRepository.deleteById(id);
    }

    @PostMapping("/signin")
    public ResponseObject login(@RequestBody LoginDTO userRequestDTO) {
      return authService.signIn(userRequestDTO);
    }

    @PostMapping("/signup")
    public ResponseObject signup(@RequestBody Users user) {
     return   authService.signUp(user);
    }

    @GetMapping ("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseObject deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
    @PostMapping("/addRole")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseObject addRole(@RequestBody RoleToUser roleToUser){
        return userService.addRoleForUser(roleToUser);
    }

    @GetMapping("/home")
    public ResponseObject loginOauth2() {

     return new ResponseObject("200","Login oauth2 successfully",null);
    }
}
