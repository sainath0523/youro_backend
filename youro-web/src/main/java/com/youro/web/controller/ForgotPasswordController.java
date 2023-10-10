package com.youro.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youro.web.pojo.BasicResponse;
import com.youro.web.pojo.LoginRequest;
import com.youro.web.service.ForgotPasswordService;
import com.youro.web.utils.OtpUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/youro/api/v1")
public class ForgotPasswordController {
	
	@Autowired
    ForgotPasswordService forgotPasswordService;
	
	@Autowired
    JavaMailSender javaMailSender;

    OtpUtils otpUtils = new OtpUtils();

	@PutMapping("/password-rest")
    public BasicResponse passwordReset(@RequestBody @Valid LoginRequest requestBody)
    {
        return forgotPasswordService.passwordReset(requestBody);
    }

    @GetMapping("/send-email")
    public String sendEmail()
    {
        SimpleMailMessage mes = new SimpleMailMessage();
        mes.setTo("rmandava1404@gmail.com");
        mes.setSubject("Test Main");
        mes.setText("Testing the mail");
        javaMailSender.send(mes);
        return "Email sent successfully";
    }

    @GetMapping("/send-otp/{emailId}")
    public String sendOtp(@PathVariable("emailId") String emailId)
    {
        String otp = otpUtils.generateOTP(6);
        SimpleMailMessage mes = new SimpleMailMessage();
        mes.setTo(emailId);
        mes.setSubject("OTP for password reset");
        mes.setText(otp);
        javaMailSender.send(mes);
        return otp;
    }
}
