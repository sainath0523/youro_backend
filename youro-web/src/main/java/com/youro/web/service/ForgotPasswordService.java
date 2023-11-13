package com.youro.web.service;

import com.youro.web.entity.*;
import com.youro.web.exception.CustomException;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Request.LoginRequest;
import com.youro.web.repository.UserRepository;
import com.youro.web.utils.OtpUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ForgotPasswordService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
        
    @Autowired
    JavaMailSender javaMailSender;
    
    OtpUtils otpUtils = new OtpUtils();

    public BasicResponse passwordReset(LoginRequest requestBody) throws CustomException
    {
        BasicResponse resp = new BasicResponse();
        Optional<User> user = userRepository.findByEmail(requestBody.username);
        if(user.isPresent())
        {
            User detail = user.get();
            detail.password = passwordEncoder.encode(requestBody.password);
            userRepository.save(detail);
        }
        else
        {
            throw new CustomException("User not Available");
        }
        resp.message = "Password Reset Successful";
        return resp;
    }

	public BasicResponse sendOpt(String emailId) {

		Optional<User> user = userRepository.findByEmail(emailId);
        
        if(user.isPresent())
        {
        	String otp = otpUtils.generateOTP(6);
	   	    SimpleMailMessage mes = new SimpleMailMessage();
	   	    mes.setTo(emailId);
	   	    mes.setSubject("OTP for password reset");
	   	    mes.setText(otp);
	   	    javaMailSender.send(mes);
	   	    return new BasicResponse(otp);
        }
        else
        {
            throw new CustomException("User not Available");
        }
		 
	}  
    
}
