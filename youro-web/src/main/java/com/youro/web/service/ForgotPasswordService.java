package com.youro.web.service;

import com.youro.web.entity.*;
import com.youro.web.exception.CustomException;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Request.LoginRequest;
import com.youro.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ForgotPasswordService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    public BasicResponse passwordReset(LoginRequest requestBody) throws CustomException
    {
    	System.out.println("In FPService's passwordReset()");
        BasicResponse resp = new BasicResponse();
        Optional<User> user = userRepository.findByEmail(requestBody.username);
        if(user.isPresent())
        {
            User detail = user.get();
            detail.password = passwordEncoder.encode(requestBody.password);
            userRepository.save(detail);
        }
        else
        {
            throw new CustomException("User not Available");
        }
        resp.message = "Password Reset Successful";
        return resp;
    }  
    
}
