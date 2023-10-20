package com.youro.web.service;

import com.youro.web.entity.User;
import com.youro.web.mapper.UserMapper;
import com.youro.web.pojo.Request.UpdateUserRequest;
import com.youro.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProviderService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    public User updateProfile(UpdateUserRequest user){
        Optional<User> temp = userRepository.findByEmail(user.email);
        User res = new User();
        if(user.email.equals(temp.get().email)){
            res = UserMapper.updateRequestToUser(user, passwordEncoder);
            return userRepository.save(res);
        }
        return res;
    }
}
