package com.example.authfirebase.Repository;

import com.example.authfirebase.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users,Long> {
    List<Users> findByEmail(String email);
    List<Users> findUsersByNameUser(String name);

}
