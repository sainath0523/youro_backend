package com.youro.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youro.web.entity.AppointmentStatus;
import com.youro.web.entity.Appointments;
import com.youro.web.entity.SymptomScore;
import com.youro.web.entity.UserType;
import com.youro.web.repository.AppointmentsRepository;
import com.youro.web.repository.SymptomScoreRepository;

@Service
public class PatientService {
	
	@Autowired
    SymptomScoreRepository symptomScoreRepo;

    @Autowired
    AppointmentsRepository appointmentsRepository;
	
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
	
	public List<Map<String, String>> getAppointments(String uType,int uId, String apptStatus){
        List<Map<String, String>> returnRes = new ArrayList<>();
        Map<String, String> item;
        if(apptStatus == null){
            item = new HashMap<>();
            item.put("test_optional_pathVar" , "Success");
            returnRes.add(item);
            return returnRes;
        }
        List<Appointments> res = new ArrayList<>();
        if(uType.equals(UserType.PATIENT.toString())){
            res.addAll( appointmentsRepository.findByPatientId(uId, getApptStatusAsInt(apptStatus)));
        }
        if(uType.equals(UserType.PROVIDER.toString())){
            res.addAll(appointmentsRepository.findByDoctorId(uId, getApptStatusAsInt(apptStatus)));
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

}
