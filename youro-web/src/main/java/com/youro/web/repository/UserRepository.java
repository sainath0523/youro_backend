package com.youro.web.repository;

import com.youro.web.entity.User;
import com.youro.web.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByUserType(UserType userType);
    
    Optional<User> findByEmail(String email);

}
