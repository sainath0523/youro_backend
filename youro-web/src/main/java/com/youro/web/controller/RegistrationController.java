package com.youro.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youro.web.entity.User;
import com.youro.web.pojo.Request.RegistrationRequest;
import com.youro.web.service.RegistrationService;

import jakarta.validation.Valid;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/youro/api/v1/")
public class RegistrationController {
	
	@Autowired
    RegistrationService registrationService;


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegistrationRequest registrationRequest) throws ParseException {
		System.out.println("In RegistrationController's register()");
		User registeredUser = registrationService.register(registrationRequest);
        return ResponseEntity.ok(registeredUser);
    }

}
