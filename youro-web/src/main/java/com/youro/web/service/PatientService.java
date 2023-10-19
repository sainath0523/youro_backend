package com.youro.web.service;

import java.util.ArrayList;
import java.util.List;

import com.youro.web.entity.*;
import com.youro.web.mapper.AppointmentMapper;
import com.youro.web.mapper.DiagnosisMapper;
import com.youro.web.mapper.QuestionnairesMapper;
import com.youro.web.mapper.SymptomScoreMapper;
import com.youro.web.pojo.Request.SymptomScoreRequest;
import com.youro.web.pojo.Response.*;
import com.youro.web.repository.DiagnosisRepository;
import com.youro.web.repository.QuestionnairesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youro.web.entity.AppointmentStatus;
import com.youro.web.entity.Appointments;
import com.youro.web.entity.SymptomScore;
import com.youro.web.pojo.Response.GetAppointmentsReponse;
import com.youro.web.pojo.Response.GetSymptomScoreResponse;

import com.youro.web.repository.AppointmentsRepository;
import com.youro.web.repository.SymptomScoreRepository;

@Service
public class PatientService {

    @Autowired
    SymptomScoreRepository symptomScoreRepo;

    @Autowired
    AppointmentsRepository appointmentsRepository;

    @Autowired
    DiagnosisRepository diagnosisRepository;

    @Autowired
    QuestionnairesRepository questionnairesRepository;
	
	public List<GetSymptomScoreResponse> getSymptomScore(int patientId){

        List<SymptomScore> res = symptomScoreRepo.findByPatientId(patientId);

        return SymptomScoreMapper.convertEntityToResPojo(res);
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

    public List<DiagnosisResponse> getAllDiagnoses(){
        List<Diagnosis> res = diagnosisRepository.findAll();
        return DiagnosisMapper.convertDiagnosisEntityToPojo(res);
    }
    public List<QuestionnairesResponse> getQuestionsByDiagId(int diagId){
        List<Questionnaires> res = questionnairesRepository.findByDiagnosisId(diagId);
        return QuestionnairesMapper.convertQuestionnairesEntityToPojo(res);
    }

    public BasicResponse saveNewSymptomScore(SymptomScoreRequest req){
        SymptomScore sC = SymptomScoreMapper.convertReqBodyToEntity(req);

        System.out.println("===============================");
        System.out.println("===============================");
        System.out.println("===============================");
        symptomScoreRepo.save(sC);
        BasicResponse res = new BasicResponse();

        res.message = "new symptom score saved";
        return res;
    }
}
