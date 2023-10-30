package com.youro.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Request.LoginRequest;
import com.youro.web.service.ForgotPasswordService;
import com.youro.web.utils.OtpUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/youro/api/v1/")
public class ForgotPasswordController {

    @Autowired
    ForgotPasswordService forgotPasswordService;

//    @PreAuthorize("hasAnyRole('ADMIN','PROVIDER','PATIENT')")
	@PutMapping("/password-reset")
    public BasicResponse passwordReset(@RequestBody @Valid LoginRequest requestBody)
    {
        return forgotPasswordService.passwordReset(requestBody);
    }

//    @PreAuthorize("hasAnyRole('ADMIN','PROVIDER','PATIENT')")
    @GetMapping("/send-otp/{emailId}")
    public BasicResponse sendOtp(@PathVariable("emailId") String emailId)
    {
    	return forgotPasswordService.sendOpt(emailId);
    }
}
