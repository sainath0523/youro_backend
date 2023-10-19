package com.youro.web.controller;

import com.youro.web.entity.User;
import com.youro.web.pojo.Request.LoginRequest;
import com.youro.web.pojo.Request.UpdateUserRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/youro/api/v1")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public BasicResponse login(@RequestBody @Valid LoginRequest requestBody) {
        return loginService.login(requestBody);
    }

    @GetMapping("/getUser/{email}")
    public User getAllUsers(@PathVariable("email") String email) {
        return loginService.getDetailsById(email);
    }

    @PutMapping("/updateUser/{uId}")
    public BasicResponse updateUser(@PathVariable("uId") int uId, @RequestBody @Valid UpdateUserRequest requestBody) {
        return new BasicResponse();
    }

}
