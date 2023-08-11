package com.example.authfirebase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user",schema = "user_service")
public class Users  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   // @Column(name = "user_name",nullable = false)
    private String nameUser;
  //  @Column(name = "pass_word",nullable = false)
    private String passWordUser;
  //  @Column(name = "full_name",nullable = false)
    private String fullName;
//    private String firstName; // bắt buộc
//    private String lastName; // bắt buộc để map vs userinfor trong oauth2
  //  @Column(name = "email",nullable = false)
    private String email;
 //   @Column(name = "role")
    private String role;
  //  @Column(name = "delete")
    private Boolean isDelete;


}
