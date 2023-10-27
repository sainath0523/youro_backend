package com.youro.web.controller;

import com.youro.web.entity.AppointmentStatus;
import com.youro.web.pojo.Request.SaveAppoitmentRequest;
import com.youro.web.pojo.Request.SymptomScoreRequest;
import com.youro.web.pojo.Response.*;
import com.youro.web.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin(origins ="*")
@RequestMapping("/youro/api/v1/")
public class PatientController {

    @Autowired
    PatientService patientService;

    //@PreAuthorize("hasRole('PATIENT')")
	@GetMapping("/symptomScore/{uId}")
    public List<GetSymptomScoreResponse> getPatientSymptomScores(@PathVariable("uId") int uId) {
        return patientService.getSymptomScore(uId);
    }

    //@PreAuthorize("hasRole('PATIENT')")
    @GetMapping({"/appointments/{uId}"})
    public GetAppointmentsResponse getUserAppointments(@PathVariable("uId") int uId, @RequestParam(name = "apptStatus", required = false) AppointmentStatus apptStatus) {
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

    @PostMapping("/saveSymptomScore")
    public SaveSymptomResponse saveSymptomScore(@RequestBody @Valid SymptomScoreRequest requestBody)
    {
        return patientService.saveSymptomScore(requestBody);
    }
    @GetMapping({"/getAvailableSlotsByDate"})
    public List<GetCustomerAvailResponse> getAvailableSlotsByDate() throws ParseException {
        return patientService.getAvailableSlotsByDate();
    }

    @PostMapping({"/saveAppointment"})
    public BasicResponse saveAppointment(@RequestBody @Valid SaveAppoitmentRequest requestBody) throws ParseException {

        return patientService.saveAppointment(requestBody);
    }



}