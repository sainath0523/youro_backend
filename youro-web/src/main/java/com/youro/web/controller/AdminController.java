package com.youro.web.controller;

import com.youro.web.entity.*;
import com.youro.web.pojo.Request.AddDescriptionRequest;
import com.youro.web.pojo.Request.AddDiagnosisRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Response.GetCarePlaneDetails;
import com.youro.web.pojo.Response.PrescriptionDetails;
import com.youro.web.service.AdminService;
import com.youro.web.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/youro/api/v1")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    LoginService loginService;


    @GetMapping("/getPrescriptions/{diagnosisId}")
    public GetCarePlaneDetails getPrescriptions(@PathVariable("diagnosisId") int diagnosisId) {
        return new GetCarePlaneDetails();
    }

    @GetMapping("/getPrescriptions/{type}")
    public List<PrescriptionDetails> getPrescriptionsByType(@PathVariable("type") PrescriptionType prescriptionType) {
        return new ArrayList<>();
    }

    @PostMapping("/addPrescription")
    public BasicResponse addPrescription(@RequestBody @Valid AddDescriptionRequest requestBody) {
        return new BasicResponse();
    }

    @PostMapping("/addDiagnosis")
    public BasicResponse addDiagnosis(@RequestBody @Valid AddDiagnosisRequest requestBody) {
        return new BasicResponse();
    }

    @GetMapping("/getAllUsers/{type}")
    public List<User> getUsersByType(@PathVariable("type") UserType uType, @RequestParam(name = "subscription", required = false) SubscriptionStatus status, @RequestParam(name = "drStatus", required = false) DoctorStatus drStatus) {

        return adminService.getUsersByType(uType);
    }


}
