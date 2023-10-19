package com.youro.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youro.web.pojo.Response.LoginResponse;
import com.youro.web.entity.User;
import com.youro.web.pojo.Request.LoginRequest;
import com.youro.web.service.JwtService;
import com.youro.web.service.LoginService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/youro/api/v1/auth")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginRequest loginRequest) {

        System.out.println("In LoginController's authenticate()");
        User authenticatedUser = loginService.authenticate(loginRequest);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

}
