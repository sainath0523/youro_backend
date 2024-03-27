package com.youro.web.controller;

import com.youro.web.entity.*;
import com.youro.web.pojo.Request.AddDiagnosisRequest;
import com.youro.web.pojo.Request.AddPrescriptionRequest;
import com.youro.web.pojo.Request.AddCategoryRequest;
import com.youro.web.pojo.Request.AddPresTypeRequest;
import com.youro.web.pojo.Request.EditCategoryRequest;
import com.youro.web.pojo.Request.UpdatePrescriptionRequest;
import com.youro.web.pojo.Request.EditPresTypeRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Response.GetCarePlaneDetails;
import com.youro.web.pojo.Response.PrescriptionDetails;
import com.youro.web.pojo.Response.QuestionnairesResponse;
import com.youro.web.pojo.Response.UserDetailsResponse;
import com.youro.web.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins ="*")
@RequestMapping("/youro/api/v1/")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/getPrescriptions/{diagnosisId}")
    public GetCarePlaneDetails getPrescriptionsByDiagId(@PathVariable("diagId") int diagnosisId) {
        return new GetCarePlaneDetails();
    }

    @GetMapping("/getAllPrescriptions")
    public List<Prescription> getAllPrescriptions() {
        return adminService.getAllPrescriptions();
    }

    @GetMapping("/getPrescriptions/{type}")
    public List<PrescriptionDetails> getPrescriptionsByType(@PathVariable("type") PrescriptionType prescriptionType) {
        return adminService.getPrescriptionDetailsByType(prescriptionType);
    }

    @PostMapping("/addPrescription")
    public BasicResponse addPrescription(@RequestBody @Valid AddPrescriptionRequest requestBody) {
        return adminService.addPrescription(requestBody);
    }

    @PutMapping("/updatePrescription/{presId}")
    public BasicResponse updatePrescription(@PathVariable("presId") int presId, @RequestBody @Valid UpdatePrescriptionRequest requestBody) {
        return adminService.updatePrescription(presId, requestBody);
    }

    @PostMapping("/addCategory")
    public BasicResponse addCategory(@RequestBody @Valid AddCategoryRequest requestBody) {
        return adminService.addCategory(requestBody);
    }

    @PostMapping("/addPresType")
    public BasicResponse addPresType(@RequestBody @Valid AddPresTypeRequest requestBody) {
        return adminService.addPresType(requestBody);
    }

    @PutMapping("/editCategory/{catId}")
    public BasicResponse editCategory(@PathVariable("catId") int catId, @RequestBody @Valid EditCategoryRequest requestBody) {
        return adminService.editCategory(catId, requestBody);
    }

    @PutMapping("/editPresType/{presTypeId}")
    public BasicResponse editPresType(@PathVariable("presTypeId") int presTypeId, @RequestBody @Valid EditPresTypeRequest requestBody) {
        return adminService.editPresType(presTypeId, requestBody);
    }

    @GetMapping("/getAllCategories")
    public List<Category> getCategories() {
        return adminService.getAllCategories();
    }

    @GetMapping("/getAllPreTypes")
    public List<PreType> getPreTypes() {
        return adminService.getAllPreTypes();
    }



    @PostMapping("/addDiagnosis")
    public BasicResponse addDiagnosis(@RequestBody @Valid AddDiagnosisRequest requestBody) {
        return adminService.addDiagnosis(requestBody);
    }

    @DeleteMapping("/deletePrescription/{presId}")
    public BasicResponse deletePrescription(@PathVariable("presId") int presId) {
        return adminService.deletePrescription(presId);
    }

    @DeleteMapping("/deleteCategory/{catId}")
    public BasicResponse deleteCategory(@PathVariable("catId") int catId) {
        return adminService.deleteCategory(catId);
    }

    @DeleteMapping("/deletePresType/{presTypeId}")
    public BasicResponse deletePresType(@PathVariable("presTypeId") int presTypeId) {
        return adminService.deletePresType(presTypeId);
    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllUsers/{type}")
    public List<UserDetailsResponse> getUsersByType(@PathVariable("type") UserType uType, @RequestParam(name = "subscription", required = false) SubscriptionStatus status, @RequestParam(name = "drStatus", required = false) DoctorStatus drStatus) throws IOException {
        return adminService.getUsersByType(uType);
    }

    @GetMapping({"/getAllQuestionnaires"})
    public List<QuestionnairesResponse> getAllQuestionnaires()  {
        return adminService.getAllQuestionnaires();
    }


}