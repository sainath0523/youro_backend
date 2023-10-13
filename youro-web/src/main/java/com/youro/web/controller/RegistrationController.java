package com.youro.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Request.RegistrationRequest;
import com.youro.web.service.RegistrationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/youro/api/v1")
public class RegistrationController {
	
	@Autowired
    RegistrationService registrationService;
	
	@PostMapping("/register")
    public BasicResponse register(@RequestBody @Valid RegistrationRequest requestBody)
    {
        return registrationService.register(requestBody);
    }

}
