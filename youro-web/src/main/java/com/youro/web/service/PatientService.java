package com.youro.web.service;

import com.youro.web.entity.AppointmentStatus;
import com.youro.web.entity.Appointments;
import com.youro.web.entity.SymptomScore;
import com.youro.web.mapper.AppointmentMapper;
import com.youro.web.mapper.SymptomScoreMapper;
import com.youro.web.pojo.Response.GetAppointmentsReponse;
import com.youro.web.pojo.Response.GetSymptomScoreResponse;
import com.youro.web.repository.AppointmentsRepository;
import com.youro.web.repository.SymptomScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    SymptomScoreRepository symptomScoreRepo;

    @Autowired
    AppointmentsRepository appointmentsRepository;

    public List<GetSymptomScoreResponse> getSymptomScore(int patientId) {
        List<SymptomScore> res = symptomScoreRepo.findByPatientId(patientId);

        return SymptomScoreMapper.getSymptomsScore(res);
    }

    public List<GetAppointmentsReponse> getAppointments(int uId, AppointmentStatus apptStatus) {
        List<Appointments> res = new ArrayList<>();
        if (apptStatus == null) {
            res.addAll(appointmentsRepository.findByUId(uId));

        } else {
            res.addAll(appointmentsRepository.findByStatus(uId, apptStatus));
        }
        return AppointmentMapper.getAppointments(res);
    }

}
