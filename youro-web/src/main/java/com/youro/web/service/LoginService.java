package com.youro.web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.youro.web.entity.User;
import com.youro.web.exception.CustomException;
import com.youro.web.pojo.Request.LoginRequest;
import com.youro.web.repository.UserRepository;

@Service
public class LoginService {
	
	@Autowired
    UserRepository userRepository;
	
	private final AuthenticationManager authenticationManager;

	public LoginService(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	public User authenticate(LoginRequest loginRequest) {
    	try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
    	}
    	catch(Exception e) {
    		throw new CustomException("Invalid Credentials");
    	}

        return userRepository.findByEmail(loginRequest.getUsername())
                .orElseThrow();
    }

}
