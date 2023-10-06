package com.youro.web.controller;

import com.youro.web.controller.request.LoginRequest;
import com.youro.web.controller.request.RegistrationRequest;
import com.youro.web.controller.response.BasicResponse;
import com.youro.web.entity.LoginTable;
import com.youro.web.service.LoginTableService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/youro/api/v1")
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
        return loginTableService.getAllUsers();
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




}
