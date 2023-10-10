package com.youro.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youro.web.pojo.BasicResponse;
import com.youro.web.pojo.LoginRequest;
import com.youro.web.service.LoginService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/youro/api/v1")
public class LoginController {
	
	@Autowired
    LoginService loginService;
	
	@PostMapping("/login")
    public BasicResponse login(@RequestBody @Valid LoginRequest requestBody)
    {
        return loginService.login(requestBody);
    }

}
