package com.youro.web.service;

import com.youro.web.pojo.Response.DoctorStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.youro.web.entity.User;
import com.youro.web.exception.CustomException;
import com.youro.web.pojo.Request.LoginRequest;
import com.youro.web.repository.UserRepository;

import java.util.Optional;

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

    public DoctorStatusResponse getDoctorStatus(int uId)
    {
        DoctorStatusResponse resp = new DoctorStatusResponse();
        Optional<User> user= userRepository.findById(uId);
        if(user.isEmpty())
        {
            throw new CustomException("User not found");
        }
        else {
            User doctor = user.get();
            resp.status = doctor.status;
            resp.uId = doctor.getUserId();
        }
        return resp;

    }

    public User getDetailsById(String emailId) throws CustomException {
        Optional<User> user = userRepository.findByEmail(emailId);
        if (user.isEmpty()) {
            throw new CustomException("No details found");
        }
        return user.get();
    }
}