package com.youro.web.controller;

import com.youro.web.entity.User;
import com.youro.web.pojo.Request.*;
import com.youro.web.pojo.Response.*;
import com.youro.web.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/youro/api/v1/")
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

    @GetMapping("/provider/getUser/{userId}")
    public User getProfile(@PathVariable("userId") String email) {
        return providerService.getProfile(email);
    }

    @PostMapping("/provider/updateProfile")
    public ResponseEntity<User> update(@RequestBody @Valid UpdateUserRequest registrationRequest)
    {
        System.out.println("In prov control update()");

        User registeredUser =  providerService.updateProfile(registrationRequest);
        return ResponseEntity.ok(registeredUser);
    }

    @PutMapping("/removeDoctorAvailability")
    public BasicResponse removeDoctorAvailability(@RequestBody AddAvailabilityRequest requestBody) throws ParseException {
        providerService.removeAvailability(requestBody);
        return new BasicResponse();
    }

    @PutMapping("/addDoctorAvailability")
    public BasicResponse addDoctorAvailability(@RequestBody AddAvailabilityRequest request)  {
        return providerService.addAvailability(request);
    }

    @PutMapping("/cancelAppointment/{apptId}/{docId}")
    public BasicResponse cancelAppointment(@PathVariable("apptId") int id, @PathVariable("docId") int docId)
    {
        return providerService.cancelAppointment(id, docId);
    }
}
