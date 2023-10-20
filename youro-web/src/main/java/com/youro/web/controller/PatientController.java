package com.youro.web.controller;

import java.util.Date;
import java.util.List;
import com.youro.web.pojo.Request.SymptomScoreRequest;
import com.youro.web.pojo.Response.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.youro.web.entity.AppointmentStatus;
import com.youro.web.pojo.Response.GetAppointmentsReponse;
import com.youro.web.pojo.Response.GetSymptomScoreResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import com.youro.web.service.PatientService;

@RestController
@RequestMapping("/youro/api/v1/")
public class PatientController {

    @Autowired
    PatientService patientService;

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/symptomScore/{uId}")
    public List<GetSymptomScoreResponse> getPatientSymptomScores(@PathVariable("uId") int uId) {
        return patientService.getSymptomScore(uId);
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping({"/appointments/{uId}"})
    public List<GetAppointmentsReponse> getUserAppointments(@PathVariable("uId") int uId, @RequestParam(name = "apptStatus", required = false) AppointmentStatus apptStatus) {
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


    @PostMapping("/saveNewAppointment")
    public BasicResponse saveNewAppointment(@RequestBody @Valid SymptomScoreRequest requestBody)
    {
        return patientService.saveNewSymptomScore(requestBody);
    }


}
