package com.youro.web.controller;

import com.youro.web.entity.User;
import com.youro.web.pojo.Request.*;
import com.youro.web.pojo.Response.*;
import com.youro.web.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/youro/api/v1")
public class ProviderController {

    @Autowired
    ProviderService providerService;
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

    @GetMapping("/getCarePlanDetails/{apptId}")
    public List<GetCarePlaneDetails> getCarePlanDetailsById(@PathVariable("apptId") int apptId) {
        return new ArrayList<>();
    }

    @PostMapping("/saveCarePlanDetails")
    public BasicResponse saveCarePlanDetails2311(@RequestBody @Valid SaveCarePlanRequest requestBody) {
        return new BasicResponse();
    }



    @PostMapping("/provider/updateProfile")
    public ResponseEntity<User> update(@RequestBody @Valid UpdateUserRequest registrationRequest)
    {
        System.out.println("In RegistrationController's register()");

        User registeredUser =  providerService.updateProfile(registrationRequest);
        return ResponseEntity.ok(registeredUser);
    }

}
