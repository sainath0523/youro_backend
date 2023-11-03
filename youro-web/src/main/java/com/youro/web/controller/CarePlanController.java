package com.youro.web.controller;

import com.youro.web.pojo.Request.SaveCarePlanRequest;
import com.youro.web.pojo.Request.SaveCheckListRequest;
import com.youro.web.pojo.Request.SaveNotesRequest;
import com.youro.web.pojo.Request.UpdateResultDetailsRequest;
import com.youro.web.pojo.Response.*;
import com.youro.web.service.CarePlanService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins ="*")
@RequestMapping("/youro/api/v1/")
public class CarePlanController {
	
	@Autowired
    CarePlanService carePlanService;

    @PostMapping("/saveCheckList")
    public BasicResponse saveCheckList(@RequestBody @Valid SaveCheckListRequest requestBody) {
        return new BasicResponse();
    }

    @GetMapping("/getCheckList/{doctorId}")
    public List<GetCheckListResponse> getCheckList(@PathVariable("doctorId") int doctorId) {   	
        return carePlanService.getCheckList(doctorId);
    }
    
    @PostMapping("/uploadResults")
    public List<byte[]> uploadResults(@RequestParam("files") MultipartFile[] files, @RequestParam("patientId") int patientId) {
    	carePlanService.uploadResults(files, patientId);
    	return carePlanService.getResults(patientId);
    }
    
    @GetMapping("/getResults/{patientId}")
    public List<byte[]> getResults(@PathVariable("patientId") int patientId) {
        return carePlanService.getResults(patientId);
    }

    @PostMapping("/saveNotes")
    public BasicResponse saveNotes(@RequestBody @Valid SaveNotesRequest requestBody) {

        return carePlanService.saveNotes(requestBody);
    }

    @GetMapping("/getNotes/{uId}")
    public List<GetNotesResponse> getNotes(@PathVariable("uId") int uId) {

        return carePlanService.getNotes(uId);
    }

    @GetMapping("/getCarePlanDetails")
    public GetCarePlaneDetails getCarePlanDetails(@RequestParam(required = false, name = "apptId") Integer apptId, @RequestParam(required = false, name = "diagId") Integer diagId) {
        return carePlanService.getCarePlaneDetails(apptId, diagId);
    }

    @GetMapping("/getCarePlanDetailsByPatientID/{uID}")
    public List<GetCarePlanByPatientResponse> getCarePlanDetailsById(@PathVariable("uID") Integer uId) {
        return carePlanService.getCarePlanByPatient(uId);
    }

    @PostMapping("/saveCarePlanDetails")
    public BasicResponse saveCarePlanDetails(@RequestBody @Valid SaveCarePlanRequest requestBody) {
        return carePlanService.saveCarePlan(requestBody);
    }
}