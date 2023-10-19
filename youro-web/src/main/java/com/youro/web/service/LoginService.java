package com.youro.web.service;

import com.youro.web.entity.User;
import com.youro.web.exception.CustomException;
import com.youro.web.pojo.Request.LoginRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public BasicResponse login(LoginRequest requestBody) throws CustomException {
        BasicResponse resp = new BasicResponse();
        Optional<User> user = userRepository.findByEmail(requestBody.username);
        if (user.isPresent()) {
            if (!user.get().password.equals(requestBody.password)) {
                throw new CustomException("Password Incorrect");
            }
        } else {
            throw new CustomException("User not Available");
        }
        resp.message = "Login Success";
        return resp;
    }

    public User getDetailsById(String emailId) throws CustomException {
        Optional<User> user = userRepository.findByEmail(emailId);
        if (user.isEmpty()) {
            throw new CustomException("No details found");
        }
        return user.get();
    }

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
