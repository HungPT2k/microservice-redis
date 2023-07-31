package com.example.authfirebase.Service;

import com.example.authfirebase.DTO.LoginDTO;
import com.example.authfirebase.DTO.ResponseObject;
import com.example.authfirebase.DTO.RoleToUser;
import com.example.authfirebase.model.Users;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<Users> getAllUser();
    Optional<Users> getById(Long id);
    ResponseObject updateUser(Users newUser,Long id);
    ResponseObject deleteUser(Long id);
    ResponseObject SignIn(LoginDTO loginRequest);
    ResponseObject SignUp(Users users);
    UserDetails ConvertUserToUserDetail(Users users);
    boolean CheckExitsUser(String userName , String email);
    public ResponseObject addRoleForUser(RoleToUser roleToUser);
    void SaveUserOauth2(String username, String email);

}
