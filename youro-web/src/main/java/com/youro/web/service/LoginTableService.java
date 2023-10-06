package com.youro.web.service;

import com.youro.web.controller.request.LoginRequest;
import com.youro.web.controller.request.RegistrationRequest;
import com.youro.web.controller.response.BasicResponse;
import com.youro.web.entity.*;
import com.youro.web.repository.AppointmentsRepository;
import com.youro.web.repository.SymptomScoreRepository;
import com.youro.web.exception.CustomException;
import com.youro.web.repository.LoginTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LoginTableService {

    @Autowired
    LoginTableRepository loginTableRepository;


    @Autowired
    SymptomScoreRepository symptomScoreRepo;

    @Autowired
    AppointmentsRepository appointmentsRepository;

    public BasicResponse login(LoginRequest requestBody) throws CustomException
    {
        BasicResponse resp = new BasicResponse();
        Optional<LoginTable> user = loginTableRepository.findById(requestBody.username);
        if(user.isPresent())
        {
            if(!user.get().password.equals(requestBody.password))
            {
                throw new CustomException("Password Incorrect");
            }
        }
        else
        {
            throw new CustomException("User not Available");
        }

        resp.message = "Login Success";
        return resp;
    }

    public BasicResponse register(RegistrationRequest requestBody) throws CustomException
    {
        BasicResponse resp = new BasicResponse();
        Optional<LoginTable> user = loginTableRepository.findById(requestBody.email);
        if(requestBody.relatedEmail !=null && !requestBody.email.isEmpty())
        {
            Optional<LoginTable> userRelated = loginTableRepository.findById(requestBody.relatedEmail);
            if(userRelated.isEmpty())
            {
                throw new CustomException("No Account found with " + requestBody.email + " email ID");
            }
        }
        if(user.isEmpty())
        {
            LoginTable userDetails = LoginTable.builder().firstName(requestBody.firstName)
                    .lastName(requestBody.lastName)
//                    .userType(UserType.valueOf(requestBody.userType))
                    .email(requestBody.email)
                    .hasInsurance(requestBody.hasInsurance)
                    .relation(requestBody.relation)
                    .relationEmail(requestBody.relatedEmail)
                    .password(requestBody.password).build();
            loginTableRepository.save(userDetails);
        }
        else
        {
            throw new CustomException("Account exists with provided Email ID");
        }

        resp.message = "Login Success";
        return resp;
    }

    public List<LoginTable> getAllUsers()
    {
        return loginTableRepository.findAll();
    }

    public List<LoginTable> getUsersByType(String uType)
    {
        UserType inpP = getUTypeEnumFromString(uType);
        return loginTableRepository.findByUserType(inpP);
    }

    private UserType getUTypeEnumFromString(String uType){
        UserType inpP = UserType.PATIENT;
        if(uType.equals(UserType.PATIENT.toString())){
            inpP = UserType.PATIENT;
        }
        if(uType.equals(UserType.ADMIN.toString())){
            inpP = UserType.ADMIN;
        }
        if(uType.equals(UserType.PROVIDER.toString())){
            inpP = UserType.PROVIDER;
        }

        return inpP;
    }

    public Optional<LoginTable> getById(String userId)
    {
        return loginTableRepository.findById(userId);
    }

    public List<Map<String, String>> getSymptomScore(int patientId){
        List<SymptomScore> res = symptomScoreRepo.findByPatientId(patientId);
        List<Map<String, String>> returnRes = new ArrayList<>();
        Map<String, String> item;

        for(SymptomScore ss : res){
            item = new HashMap<>();
            item.put("scoreId", ss.scoreId+ "");
            item.put("patientId", ss.getPatientId().userId + "");
            item.put("diagnosisId", ss.getDiagnosis().diagId + "");
            item.put("symptomScore", ss.symptomScore+"");
            item.put("questionData", ss.questionData);
            item.put("dateTime", ss.dateTime+"");

            returnRes.add(item);
        }
        return returnRes;
    }

    private int getApptStatusAsInt(String apptStatus){
        int res = -1;
        if(apptStatus.equals(AppointmentStatus.COMPLETED.toString())){
            res = 0;
        }
        else if(apptStatus.equals(AppointmentStatus.SCHEDULED.toString())){
            res = 1;
        }
        else if(apptStatus.equals(AppointmentStatus.CANCELED.toString())){
            res = 2;
        }
        else if(apptStatus.equals(AppointmentStatus.UNATTENDED.toString())){
            res = 3;
        }
        return res;
    }

    public List<Map<String, String>> getAppointments(String uType,int uId, String apptStatus){
        List<Map<String, String>> returnRes = new ArrayList<>();
        Map<String, String> item;
        List<Appointments> res = new ArrayList<>();
        if(uType.equals(UserType.PATIENT.toString())){
            res.addAll( appointmentsRepository.findByPatientId(uId, getApptStatusAsInt(apptStatus)));
        }
        if(uType.equals(UserType.PROVIDER.toString())){
            res.addAll(appointmentsRepository.findByDoctorId(uId, getApptStatusAsInt(apptStatus)));
        }
        for(Appointments app : res){
            item = new HashMap<>();
            item.put("apptId", app.apptId+ "");
            item.put("patientId", app.getPatientId().userId + "");
            item.put("doctorId", app.getDoctorId().userId + "");
            item.put("apptDate", app.apptDate+"");
            item.put("apptStartTime", app.apptStartTime+"");
            item.put("apptEndTime", app.apptEndTime+"");
            item.put("link", app.link);
            item.put("status", app.status+"");

            returnRes.add(item);
        }
        System.out.println(res);
        return returnRes;
    }


}
