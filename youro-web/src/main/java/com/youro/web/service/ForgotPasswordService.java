package com.youro.web.service;

import com.youro.web.entity.*;
import com.youro.web.exception.CustomException;
import com.youro.web.pojo.BasicResponse;
import com.youro.web.pojo.LoginRequest;
import com.youro.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ForgotPasswordService {

    @Autowired
    UserRepository userRepository;

    public BasicResponse passwordReset(LoginRequest requestBody) throws CustomException
    {
        BasicResponse resp = new BasicResponse();
        Optional<User> user = userRepository.findById(requestBody.username);
        if(user.isPresent())
        {
            User detail = user.get();
            detail.password = requestBody.password;
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
