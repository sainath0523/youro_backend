package com.youro.web.controller;

import com.youro.web.entity.User;
import com.youro.web.pojo.Request.AddAvailabilityRequest;
import com.youro.web.pojo.Request.UpdateUserRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Response.DoctorAvailabilityResponse;
import com.youro.web.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.TimeZone;

@RestController
@CrossOrigin(origins ="*")
@RequestMapping("/youro/api/v1/")
public class ProviderController {

    @Autowired
    ProviderService providerService;

    @GetMapping("/getUser/{userId}")
    public User getProfile(@PathVariable("userId") int userId) {
        return providerService.getProfile(userId);
    }

    @PutMapping("/provider/updateProfile")
    public ResponseEntity<User> updateProfile(@RequestBody @Valid UpdateUserRequest registrationRequest)
    {
        System.out.println("In prov control update()");
        User registeredUser =  providerService.updateProfile(registrationRequest);
        return ResponseEntity.ok(registeredUser);
    }

    @PutMapping("/removeDoctorAvailability")
    public BasicResponse removeDoctorAvailability(@RequestBody AddAvailabilityRequest requestBody) throws ParseException {
       return providerService.removeAvailability(requestBody);

    }

    @PutMapping("/addDoctorAvailability")
    public BasicResponse addDoctorAvailability(@RequestBody AddAvailabilityRequest request)  {
        return providerService.addAvailability(request);
    }

    @PutMapping("/cancelAppointment/{apptId}/{docId}")
        public BasicResponse cancelAppointment(@PathVariable("apptId") int id, @PathVariable("docId") int docId)
    {
        System.out.println(id + " :: " + docId);
        return providerService.cancelAppointment(id, docId);
    }

    @PutMapping("/updateAppointment/{apptId}")
    public BasicResponse updateAppointments(@PathVariable("apptId") int id, @RequestParam(required = true, name = "link") String link)
    {
        //System.out.println(id + " :: " + docId);
        return providerService.updateAppointment(id, link);
    }

    @GetMapping("/getAvailability/{docId}")
    public DoctorAvailabilityResponse getAvailability(@PathVariable("docId") int docId)
    {
        System.out.println("Default TimeZone :" + TimeZone.getDefault());
        return providerService.getAvailability(docId);
    }

    @GetMapping("/getPatientsByDoctor/{docId}")
    public List<User> getUsersByType(@PathVariable("docId") int uId) {
        return providerService.getUsersByDoctor(uId);
    }

}