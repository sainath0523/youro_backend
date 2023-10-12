package com.youro.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youro.web.service.PatientService;

@RestController
@RequestMapping("/youro/api/v1")
public class PatientController {
	
    @Autowired
    PatientService patientService;
	
	@GetMapping("/symptomScore/{uId}")
    public List<Map<String, String>> getPatientSymptomScores(@PathVariable("uId") int uId) {
        return patientService.getSymptomScore(uId);
    }

    @GetMapping({"/appointments/{uType}/{uId}/{apptStatus}", "/appointments/{uType}/{uId}"})
    public List<Map<String, String>> getUserAppointments(@PathVariable("uType") String uType, @PathVariable("uId") int uId, @PathVariable(value="apptStatus", required = false) String apptStatus) {
        return patientService.getAppointments(uType, uId, apptStatus);
    }

}
