package com.youro.web.service;

import com.youro.web.conversion.LoginTableConversion;
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
    LoginTableConversion loginTableConversion;

    @Autowired
    AppointmentsRepository appointmentsRepository;

    public BasicResponse login(LoginRequest requestBody) throws CustomException
    {
        BasicResponse resp = new BasicResponse();
        Optional<LoginTable> user = loginTableRepository.findByEmail(requestBody.username);
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

    public BasicResponse passwordReset(LoginRequest requestBody) throws CustomException
    {
        BasicResponse resp = new BasicResponse();
        Optional<LoginTable> user = loginTableRepository.findById(requestBody.username);
        if(user.isPresent())
        {
            LoginTable detail = user.get();
            detail.password = requestBody.password;
            loginTableRepository.save(detail);
        }
        else
        {
            throw new CustomException("User not Available");
        }
        resp.message = "Password Reset Successful";
        return resp;
    }

    public BasicResponse register(RegistrationRequest requestBody) throws CustomException
    {
        BasicResponse resp = new BasicResponse();
        Optional<LoginTable> user = loginTableRepository.findByEmail(requestBody.email);
        if(requestBody.relationEmail !=null && !requestBody.email.isEmpty())
        {
            Optional<LoginTable> userRelated = loginTableRepository.findByEmail(requestBody.relationEmail);
            if(userRelated.isEmpty())
            {
                throw new CustomException("No Account found with " + requestBody.email + " email ID");
            }
        }
        if(user.isEmpty())
        {
            LoginTable userDetails = loginTableConversion.toLoginTable(requestBody);
            loginTableRepository.save(userDetails);
        }
        else
        {
            throw new CustomException("Account exists with provided Email ID");
        }

        resp.message = "Registration Success";
        return resp;
    }

    public List<LoginTable> getAllUsers()
    {
        return loginTableRepository.findAll();
    }

    public LoginTable getDetailsById(String emailId) throws CustomException
    {
        Optional<LoginTable> user = loginTableRepository.findByEmail(emailId);
        if(user.isEmpty())
        {
            throw new CustomException("No details found");
        }
        return user.get();
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

    public List<Map<String, String>> getSymptomScore(int patientId){
        List<SymptomScore> res = symptomScoreRepo.findByPatientId(patientId);
        List<Map<String, String>> returnRes = new ArrayList<>();
        Map<String, String> item;

        for(SymptomScore ss : res){
            item = new HashMap<>();
            item.put("scoreId", ss.getScoreId()+ "");
            item.put("patientId", ss.getPatientId().getUserId() + "");
            item.put("diagnosisId", ss.getDiagnosis().getDiagId() + "");
            item.put("symptomScore", ss.getSymptomScore()+"");
            item.put("questionData", ss.getQuestionData());
            item.put("dateTime", ss.getDateTime()+"");

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
        boolean hasStatus = apptStatus != null;
        if(hasStatus){
            if(uType.equals(UserType.PATIENT.toString())){
                res.addAll( appointmentsRepository.findByPatientIdAndStatus(uId, getApptStatusAsInt(apptStatus)));
            }
            if(uType.equals(UserType.PROVIDER.toString())){
                res.addAll(appointmentsRepository.findByDoctorIdAndStatus(uId, getApptStatusAsInt(apptStatus)));
            }
        }
        else{
            if(uType.equals(UserType.PATIENT.toString())){
                res.addAll( appointmentsRepository.findByPatientId(uId));
            }
            if(uType.equals(UserType.PROVIDER.toString())){
                res.addAll(appointmentsRepository.findByDoctorId(uId));
            }
        }
        for(Appointments app : res){
            String patPrefix = app.getPatientId().getGender().toString().equals("MALE") ? "Mr. " : "Ms. ";
            String docPrefix = app.getDoctorId().getGender().toString().equals("MALE") ? "Mr. " : "Ms. ";
            item = new HashMap<>();
            item.put("apptId", app.getApptId()+ "");
            item.put("patientId", app.getPatientId().getUserId() + "");
            item.put("doctorName", docPrefix + app.getDoctorId().getLastName());
            item.put("patientName", patPrefix + app.getPatientId().getLastName());
            item.put("doctorId", app.getDoctorId().getUserId() + "");
            item.put("apptDate", app.getApptDate()+"");
            item.put("apptStartTime", app.getApptStartTime()+"");
            item.put("apptEndTime", app.getApptEndTime()+"");
            item.put("link", app.getLink());
            item.put("status", app.getStatus()+"");

            returnRes.add(item);
        }
        System.out.println(res);
        return returnRes;
    }


}
