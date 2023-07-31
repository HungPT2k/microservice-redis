package com.example.authfirebase.Controller;

import com.example.authfirebase.DTO.LoginDTO;
import com.example.authfirebase.DTO.ResponseObject;
import com.example.authfirebase.DTO.RoleToUser;
import com.example.authfirebase.Repository.UserRepository;
import com.example.authfirebase.Service.UserService;
import com.example.authfirebase.model.Users;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")

public class testController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
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
      return userService.SignIn(userRequestDTO);
    }

    @PostMapping("/signup")
    public ResponseObject signup(@RequestBody Users user) {
     return   userService.SignUp(user);
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
//    @GetMapping("/hello")
//    public ResponseObject loginOauth2(@AuthenticationPrincipal OAuth2User principal) {
////        if (principal != null) {
////            String name =  principal.getAttribute("name");
////            String email =  principal.getAttribute("email");
////            userService.SaveUserOauth2(name,email);
////       List<Users> users=    userRepository.findUsersByNameUser(name);
////       if (users.isEmpty()) return new ResponseObject("404","not found",null);
////            LoginDTO loginDTO = new LoginDTO();
////            loginDTO.setUserName(users.get(0).getNameUser());
////            loginDTO.setPassWordUser(users.get(0).getPassWordUser());
////            return userService.SignIn(loginDTO);
////
////        }
//        String email=principal.getAttribute("email");
//        System.out.println(email);
//     return new ResponseObject("404","not found",principal);
//    }
}
