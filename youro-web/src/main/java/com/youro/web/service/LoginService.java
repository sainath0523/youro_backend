package com.youro.web.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youro.web.entity.User;
import com.youro.web.exception.CustomException;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Request.LoginRequest;
import com.youro.web.repository.UserRepository;

@Service
public class LoginService {
	
	@Autowired
    UserRepository userRepository;
	
	public BasicResponse login(LoginRequest requestBody) throws CustomException
    {
        BasicResponse resp = new BasicResponse();
        Optional<User> user = userRepository.findByEmail(requestBody.username);
        if(user.isPresent())
        {
            if(!user.get().password.equals(requestBody.password))
            {
                throw new CustomException("Password Incorrect");
            }
        }
        else
        {
            throw new CustomException("User not Available");
        }
        resp.message = "Login Success";
        return resp;
    }

}
