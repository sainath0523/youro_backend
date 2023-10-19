package com.youro.web.controller;

import java.util.List;
import java.util.Map;

import com.youro.web.entity.SymptomScore;
import com.youro.web.pojo.Request.RegistrationRequest;
import com.youro.web.pojo.Request.SymptomScoreRequest;
import com.youro.web.pojo.Response.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping({"/getAllDiagnoses"})
    public List<DiagnosisResponse> getAllDiagnoses()  {
        return patientService.getAllDiagnoses();
    }

    @GetMapping({"/getQuestionsBydiagId/{diagId}"})
    public List<QuestionnairesResponse> getQuestionsBydiagId(@PathVariable("diagId") int diagId)  {
        return patientService.getQuestionsByDiagId(diagId);
    }

    @PostMapping("/saveNewSymptomScore")
    public BasicResponse saveNewSymptomScore(@RequestBody @Valid SymptomScoreRequest requestBody)
    {

        return patientService.saveNewSymptomScore(requestBody);
    }


}
