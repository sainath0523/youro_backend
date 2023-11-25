package com.youro.web.controller;

import com.youro.web.pojo.Request.SaveCarePlanRequest;
import com.youro.web.pojo.Request.SaveNotesRequest;
import com.youro.web.pojo.Response.*;
import com.youro.web.service.CarePlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins ="*")
@RequestMapping("/youro/api/v1/")
public class CarePlanController {
	
	@Autowired
    CarePlanService carePlanService;

   /* @PostMapping("/saveCheckList")
    public BasicResponse saveCheckList(@RequestBody @Valid SaveCheckListRequest requestBody) {
        return new BasicResponse();
    }*/

    @GetMapping("/getCheckList/{doctorId}")
    public List<GetCheckListResponse> getCheckList(@PathVariable("doctorId") int doctorId) {   	
        return carePlanService.getCheckList(doctorId);
    }

    @PostMapping("/saveNotes")
    public BasicResponse saveNotes(@RequestBody @Valid SaveNotesRequest requestBody) {
        return carePlanService.saveNotes(requestBody);
    }

    @GetMapping("/getNotes/{uId}")
    public List<GetNotesResponse> getNotes(@PathVariable("uId") int uId) {
        return carePlanService.getNotes(uId);
    }

    @GetMapping("/getCarePlanVersions/{apptID}")
    public List<GetCarePlanVersions> getCarePlanVersions(@PathVariable("apptID") Integer apptId) {
        return carePlanService.getCarePlanVersions(apptId);
    }

    @GetMapping("/getCarePlanDetailsById/{diagId}")
    public GetCarePlaneDetails getCarePlanDetailsByDiagId(@PathVariable("diagId") Integer diagId) {
        return carePlanService.getCarePlaneDetailsById(diagId);
    }

    @GetMapping("/getCarePlanDetails/{cId}")
    public GetCarePlanResponse getCarePlanVersions(@PathVariable("cId") Integer cId, @RequestParam(required = false, name ="edit") Boolean edit) {
        return carePlanService.getCarePlanById(cId, edit);
    }

    @PostMapping("/saveCarePlanDetails")
    public BasicResponse saveCarePlanDetails(@RequestBody @Valid SaveCarePlanRequest requestBody) {
        return carePlanService.saveCarePlan(requestBody);
    }

    @GetMapping("/getLatestCarePlanByPatient")
    public GetCarePlanResponse getLatestCarePlanByPatient(@RequestParam(required = false, name = "patientId") Integer uId, @RequestParam(required = false, name ="apptId") Integer apptId) {
        return carePlanService.getCarePlanByPatient(uId, apptId);
    }


}