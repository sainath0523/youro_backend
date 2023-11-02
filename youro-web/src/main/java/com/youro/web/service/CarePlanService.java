package com.youro.web.service;

import com.youro.web.entity.*;
import com.youro.web.exception.CustomException;
import com.youro.web.mapper.CarePlanMapper;
import com.youro.web.mapper.NotesMapper;
import com.youro.web.pojo.Request.SaveCarePlanRequest;
import com.youro.web.pojo.Request.SaveNotesRequest;
import com.youro.web.pojo.Response.*;
import com.youro.web.repository.*;
import com.youro.web.utils.HelpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CarePlanService {

    @Autowired
    CarePlanRepository carePlanRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    NotesRepository notesRepository;

    @Autowired
    DiagnosisRepository diagnosisRepository;

    @Autowired
    AppointmentsRepository appointmentsRepository;


    public GetCarePlaneDetails getPrescriptionByDiagId(int diagId)
    {

        Diagnosis diag = HelpUtils.getDiagnosis(diagId);
        List<PrescriptionType> prescriptionTypes = new ArrayList<>();
        prescriptionTypes.add(PrescriptionType.VITAMINS);
        prescriptionTypes.add(PrescriptionType.MEDICINES);
        prescriptionTypes.add(PrescriptionType.LIFESTYLE);
        List<Prescription> prescriptionList = prescriptionRepository.findByPresTypeInAndDiagnosis(prescriptionTypes, diag);
        prescriptionList.addAll(prescriptionRepository.findByPresType(PrescriptionType.LAB));
        prescriptionList.addAll(prescriptionRepository.findByPresType(PrescriptionType.IMAGING));
        return CarePlanMapper.mapPrescriptionEntity(prescriptionList);
    }

    public GetCarePlaneDetails getCarePlanByApptId(int apptId)
    {
        Appointments appointments= HelpUtils.getAppointments(apptId);
        List<CarePlan> carePlanList = carePlanRepository.findByAppointments(appointments);
        return CarePlanMapper.mapCarePlanEntity(carePlanList);
    }


    public GetCarePlaneDetails getCarePlaneDetails(Integer apptId, Integer diagID)
    {
        if(apptId == null && diagID == null)
        {
            throw new CustomException("Please provide appointment or diagnosis ID");
        }
        if(apptId == null)
        {
            return getPrescriptionByDiagId(diagID);
        }
        else if (diagID == null)
        {
            return getCarePlanByApptId(apptId);
        }
        else {
            return CarePlanMapper.getCarePlaneDetails(getPrescriptionByDiagId(diagID), getCarePlanByApptId(apptId));
        }
    }

    public List<GetCarePlanByPatientResponse> getCarePlanByPatient(int uId)
    {
        List<GetCarePlanByPatientResponse> responses = new ArrayList<>();
        List<Integer[]> carePlanIDs = carePlanRepository.findByUserId(uId);
        for(Integer[] res : carePlanIDs)
        {
            GetCarePlanByPatientResponse patientResponse = new GetCarePlanByPatientResponse();
            Appointments appt = appointmentsRepository.findById(res[0]).get();
            Diagnosis diag = diagnosisRepository.findById(res[1]).get();
            patientResponse.diagId = diag.getDiagId();
            patientResponse.diagnosisName = diag.getName();
            patientResponse.apptId = appt.getApptId();
            patientResponse.apptDate = appt.getApptDate();
            responses.add(patientResponse);
        }
        responses.sort(Comparator.comparing(GetCarePlanByPatientResponse :: getApptDate, Comparator.reverseOrder()));
        return responses;
    }


    public List<GetNotesResponse> getNotes(int uId)
    {
        List<Notes> notes = notesRepository.findByPatientId(HelpUtils.getUser(uId));

        return NotesMapper.entityToResponseMapping(notes);
    }


    public BasicResponse saveNotes(SaveNotesRequest request)
    {
        notesRepository.save(NotesMapper.requestToEntityMapper(request));
        return new BasicResponse("Notes Saved Successfully");
    }

    public BasicResponse saveCarePlan(SaveCarePlanRequest request)
    {
        carePlanRepository.saveAll(CarePlanMapper.toCarePlanEntity(request));
        return new BasicResponse("CarePlan Saved Successfully");
    }
}