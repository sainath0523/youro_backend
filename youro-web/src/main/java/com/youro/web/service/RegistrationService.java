package com.youro.web.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public User register(RegistrationRequest registrationRequest) throws CustomException {
        Optional<User> user = userRepository.findByEmail(registrationRequest.email);
        if(registrationRequest.relationEmail !=null && !registrationRequest.email.isEmpty())
        {
            Optional<User> userRelated = userRepository.findByEmail(registrationRequest.relationEmail);
            if(userRelated.isEmpty())
            {
                throw new CustomException("No Account found with " + registrationRequest.relationEmail + " email ID");
            }
        }
        if(registrationRequest.dateOfBirth !=null && !registrationRequest.dateOfBirth.isEmpty())
        {
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(registrationRequest.dateOfBirth);
            } catch (ParseException e) {
                throw new CustomException("Invalid Date : " + registrationRequest.dateOfBirth);
            }

        }
        if(user.isEmpty())
        {
            User registeredUser = null;
            try {
                registeredUser = userMapper.toUser(registrationRequest, passwordEncoder);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return userRepository.save(registeredUser);
        }
        else
        {
            throw new CustomException("Account already exists with provided Email ID");
        }
    }


	public BasicResponse verifyUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        user.get().setVerified(true);
        userRepository.save(user.get());
        return new BasicResponse("User verified: " + email);
	}

}
