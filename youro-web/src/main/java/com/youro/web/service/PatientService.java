package com.youro.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.youro.web.entity.*;
import com.youro.web.mapper.*;
import com.youro.web.pojo.Request.SymptomScoreRequest;
import com.youro.web.pojo.Response.*;
import com.youro.web.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youro.web.entity.AppointmentStatus;
import com.youro.web.entity.Appointments;
import com.youro.web.entity.SymptomScore;
import com.youro.web.pojo.Response.GetAppointmentsReponse;
import com.youro.web.pojo.Response.GetSymptomScoreResponse;

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

    @Autowired
    DoctorScheduleRepository doctorScheduleRepository;
	
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
        symptomScoreRepo.save(sC);
        BasicResponse res = new BasicResponse();

        System.out.println("===============================");
        System.out.println("===============================");
        System.out.println("===============================");
        res.message = "new symptom score saved";
        return res;
    }

    public List<AvailableSlotsByDateResponse> getAvailableSlotsByDate(Date inpDate){
        List<DoctorSchedule> output = doctorScheduleRepository.findBySchDate(inpDate);
        List<AvailableSlotsByDateResponse> res = DoctorSchToSlotsMapper.convertDoctorSchToSlots(output);
        return res;
    }


    public BasicResponse saveNewAppointment(SymptomScoreRequest req){
        SymptomScore sC = SymptomScoreMapper.convertReqBodyToEntity(req);
        symptomScoreRepo.save(sC);
        BasicResponse res = new BasicResponse();

        System.out.println("===============================");
        System.out.println("===============================");
        System.out.println("===============================");
        res.message = "new symptom score saved";
        return res;
    }
}
