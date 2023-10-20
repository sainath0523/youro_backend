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

    public User getProfile(String email){
        Optional<User> res = userRepository.findByEmail(email);
        return res.get();
    }
    public User updateProfile(UpdateUserRequest user){
        System.out.println("+++++=========+++++");
        System.out.println("+++++=========+++++");
        Optional<User> temp = userRepository.findByEmail(user.email);
        System.out.println("+++++=========+++++");
        System.out.println("+++++=========+++++");
        System.out.println(temp.get().getEmail());
        System.out.println("+++++=========+++++");
        System.out.println("+++++=========+++++");

        User res = new User();
        if(user.email.equals(temp.get().email)){
            res = UserMapper.updateRequestToUser(user, passwordEncoder);
            res.userId = temp.get().getUserId();
            return userRepository.save(res);
        }
        return res;
    }
}
