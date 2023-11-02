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

    @GetMapping("/getCheckList/{uId}")
    public List<GetCheckListResponse> getCheckList(@PathVariable("uId") int uId) {
        return new ArrayList<GetCheckListResponse>();
    }

    @PostMapping("/uploadResults")
    public List<UploadResultsResponse> uploadResults(@RequestParam("files") MultipartFile[] files) {
        return new ArrayList<>();
    }

    @PostMapping("/updateResultsDetails")
    public BasicResponse updateResultsDetails(@RequestBody @Valid UpdateResultDetailsRequest requestBody) {
        return new BasicResponse();
    }

    @GetMapping("/getResults/{apptId}")
    public List<byte[]> getResults(@PathVariable("apptId") int apptId) {
        return new ArrayList<>();
    }


    @PostMapping("/saveNotes")
    public BasicResponse saveNotes(@RequestBody @Valid SaveNotesRequest requestBody) {
        return new BasicResponse();
    }

    @GetMapping("/getNotes/{uId}")
    public List<GetNotesResponse> getNotes(@PathVariable("uId") int uId) {
        return new ArrayList<>();
    }

    @GetMapping("/getCarePlanDetails")
    public GetCarePlaneDetails getCarePlanDetails(@RequestParam(required = false, name = "apptId") int apptId, @RequestParam(required = false, name = "diagId") int diagId) {

        return carePlanService.getCarePlaneDetails(apptId, diagId);
    }

    @PostMapping("/saveCarePlanDetails")
    public BasicResponse saveCarePlanDetails2311(@RequestBody @Valid SaveCarePlanRequest requestBody) {
        return new BasicResponse();
    }
}