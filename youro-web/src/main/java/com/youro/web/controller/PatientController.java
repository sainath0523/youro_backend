package com.youro.web.controller;

import java.util.List;
import java.util.Map;

import com.youro.web.pojo.Response.GetAppointmentsReponse;
import com.youro.web.pojo.Response.GetSymptomScoreResponse;
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
    public List<GetSymptomScoreResponse> getPatientSymptomScores(@PathVariable("uId") int uId) {
        return patientService.getSymptomScore(uId);
    }

    @GetMapping({"/appointments/{uId}/{apptStatus}", "/appointments/{uId}"})
    public List<GetAppointmentsReponse> getUserAppointments(@PathVariable("uId") int uId, @PathVariable(value="apptStatus", required = false) String apptStatus) {
        return patientService.getAppointments(uId, apptStatus);
    }

}
