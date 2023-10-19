package com.youro.web.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.youro.web.entity.User;
import com.youro.web.exception.CustomException;
import com.youro.web.mapper.UserMapper;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Request.RegistrationRequest;
import com.youro.web.repository.UserRepository;

@Service
public class RegistrationService {
	
	@Autowired
    UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
    UserMapper userMapper;
	
	public User register(RegistrationRequest registrationRequest) throws CustomException
    {
        Optional<User> user = userRepository.findByEmail(registrationRequest.email);
        if(registrationRequest.relationEmail !=null && !registrationRequest.email.isEmpty())
        {
            Optional<User> userRelated = userRepository.findByEmail(registrationRequest.relationEmail);
            if(userRelated.isEmpty())
            {
                throw new CustomException("No Account found with " + registrationRequest.relationEmail + " email ID");
            }
        }
        if(user.isEmpty())
        {
        	User registeredUser = userMapper.toUser(registrationRequest, passwordEncoder);
            return userRepository.save(registeredUser);
        }
        else
        {
            throw new CustomException("Account already exists with provided Email ID");
        }
    }

}
