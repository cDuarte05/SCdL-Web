package com.SCdl.web.login.repositories;

import com.SCdl.web.login.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, String>{
    Optional<User> findByEmail(String email);

}
