package com.youro.web.controller;

import com.youro.web.controller.request.LoginRequest;
import com.youro.web.controller.request.RegistrationRequest;
import com.youro.web.controller.response.BasicResponse;
import com.youro.web.entity.LoginTable;
import com.youro.web.service.LoginTableService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/youro/api/v1")
@CrossOrigin
public class YouroController {

    @Autowired
    LoginTableService loginTableService;

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

    @GetMapping("/getDetails")
    public List<LoginTable> getAllUsers()
    {
        return loginTableService.get();
    }
    
//    @GetMapping("/getUser/{id}")
    @RequestMapping(value = "/getUser/{uId}", method = RequestMethod.GET)
    public Optional<LoginTable> getAllUsers(@PathVariable String uId)
    {
        return loginTableService.getById(uId);
    }

}
