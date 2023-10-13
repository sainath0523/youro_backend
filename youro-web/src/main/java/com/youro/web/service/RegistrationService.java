package com.youro.web.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    UserMapper userMapper;
	
	public BasicResponse register(RegistrationRequest requestBody) throws CustomException
    {
        BasicResponse resp = new BasicResponse();
        Optional<User> user = userRepository.findByEmail(requestBody.email);
        if(requestBody.relationEmail !=null && !requestBody.email.isEmpty())
        {
            Optional<User> userRelated = userRepository.findByEmail(requestBody.relationEmail);
            if(userRelated.isEmpty())
            {
                throw new CustomException("No Account found with " + requestBody.relationEmail + " email ID");
            }
        }
        if(user.isEmpty())
        {
            User userDetails = userMapper.toUser(requestBody);
            userRepository.save(userDetails);
        }
        else
        {
            throw new CustomException("Account exists with provided Email ID");
        }

        resp.message = "Registration Success";
        return resp;
    }

}
