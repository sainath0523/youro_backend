package com.youro.web.controller;

import java.util.Date;
import java.util.List;
<<<<<<< HEAD
import com.youro.web.pojo.Request.SymptomScoreRequest;
import com.youro.web.pojo.Response.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.youro.web.entity.AppointmentStatus;
=======

>>>>>>> refs/remotes/origin/dev_jwt
import com.youro.web.pojo.Response.GetAppointmentsReponse;
import com.youro.web.pojo.Response.GetSymptomScoreResponse;
<<<<<<< HEAD
import org.springframework.security.access.prepost.PreAuthorize;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> refs/remotes/origin/dev_jwt

import com.youro.web.service.PatientService;

@RestController
<<<<<<< HEAD
@RequestMapping("/youro/api/v1/")
=======
@RequestMapping("/youro/api/v1/patient")
>>>>>>> refs/remotes/origin/dev_jwt
public class PatientController {

    @Autowired
    PatientService patientService;
<<<<<<< HEAD

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/symptomScore/{uId}")
=======
    
    @PreAuthorize("hasRole('PATIENT')")
	@GetMapping("/symptomScore/{uId}")
>>>>>>> refs/remotes/origin/dev_jwt
    public List<GetSymptomScoreResponse> getPatientSymptomScores(@PathVariable("uId") int uId) {
        return patientService.getSymptomScore(uId);
    }

    @PreAuthorize("hasRole('PATIENT')")
<<<<<<< HEAD
    @GetMapping({"/appointments/{uId}"})
    public List<GetAppointmentsReponse> getUserAppointments(@PathVariable("uId") int uId, @RequestParam(name = "apptStatus", required = false) AppointmentStatus apptStatus) {
=======
    @GetMapping({"/appointments/{uId}/{apptStatus}", "/appointments/{uId}"})
    public List<GetAppointmentsReponse> getUserAppointments(@PathVariable("uId") int uId, @PathVariable(value="apptStatus", required = false) String apptStatus) {
>>>>>>> refs/remotes/origin/dev_jwt
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
    @GetMapping({"/getAvailableSlotsByDate/{selDate}"})
    public List<AvailableSlotsByDateResponse> getAvailableSlotsByDate(@PathVariable("selDate") Date selDate){
        return patientService.getAvailableSlotsByDate(selDate);
    }

}
