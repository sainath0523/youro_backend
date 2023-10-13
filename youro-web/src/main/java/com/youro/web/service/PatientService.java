package com.youro.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.youro.web.mapper.AppointmentMapper;
import com.youro.web.mapper.SymptomScoreMapper;
import com.youro.web.pojo.Response.GetAppointmentsReponse;
import com.youro.web.pojo.Response.GetSymptomScoreResponse;
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
	
	public List<GetSymptomScoreResponse> getSymptomScore(int patientId){
        List<SymptomScore> res = symptomScoreRepo.findByPatientId(patientId);

        return SymptomScoreMapper.getSymptomsScore(res);
    }
	
	public List<GetAppointmentsReponse> getAppointments(int uId, String apptStatus){
        List<Appointments> res = new ArrayList<>();
        if(apptStatus == null)
        {
            res.addAll( appointmentsRepository.findByUId(uId));

        }else
        {
            res.addAll(appointmentsRepository.findByStatus(uId, getApptStatusAsInt(apptStatus)));
        }
        return AppointmentMapper.getAppointments(res);
    }
	
	private static int getApptStatusAsInt(String apptStatus){
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
