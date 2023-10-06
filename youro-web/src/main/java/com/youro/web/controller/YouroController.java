package com.youro.web.controller;

import com.youro.web.utils.OtpUtils;
import com.youro.web.controller.request.LoginRequest;
import com.youro.web.controller.request.RegistrationRequest;
import com.youro.web.controller.response.BasicResponse;
import com.youro.web.entity.LoginTable;
import com.youro.web.service.LoginTableService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/youro/api/v1")
public class YouroController {

    @Autowired
    LoginTableService loginTableService;

    @Autowired
    JavaMailSender javaMailSender;

    OtpUtils otpUtils = new OtpUtils();

    @PostMapping("/login")
    public BasicResponse login(@RequestBody @Valid LoginRequest requestBody)
    {
        return loginTableService.login(requestBody);
    }

    @PostMapping("/register")
    public BasicResponse register(@RequestBody @Valid RegistrationRequest requestBody)
    {
        return loginTableService.register(requestBody);
    }

    @GetMapping("/getDetails/{id}")
    public LoginTable getAllUsers(@PathVariable("id") String id)
    {
        return loginTableService.getDetailsById(id);
    }

    @GetMapping("/allUsers/{type}")
    public List<LoginTable> getUsersByType(@PathVariable("type") String uType) {
        return loginTableService.getUsersByType(uType);
    }

    @GetMapping("/symptomScore/{uId}")
    public List<Map<String, String>> getPatientSymptomScores(@PathVariable("uId") int uId) {
        return loginTableService.getSymptomScore(uId);
    }

    @GetMapping({"/appointments/{uType}/{uId}/{apptStatus}", "/appointments/{uType}/{uId}"})
    public List<Map<String, String>> getUserAppointments(@PathVariable("uType") String uType, @PathVariable("uId") int uId, @PathVariable(value="apptStatus", required = false) String apptStatus) {
        return loginTableService.getAppointments(uType, uId, apptStatus);
    }

    @PutMapping("/password-rest")
    public BasicResponse passwordReset(@RequestBody @Valid LoginRequest requestBody)
    {
        return loginTableService.passwordReset(requestBody);
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
