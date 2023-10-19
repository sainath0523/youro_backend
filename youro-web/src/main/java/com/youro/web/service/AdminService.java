package com.youro.web.service;

import com.youro.web.entity.User;
import com.youro.web.entity.UserType;
import com.youro.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    public List<User> getUsersByType(UserType uType) {
        return userRepository.findByUserType(uType);
    }


}
