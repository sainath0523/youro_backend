package com.youro.web.service;

import com.youro.web.entity.*;
import com.youro.web.exception.CustomException;
import com.youro.web.mapper.CarePlanMapper;
import com.youro.web.pojo.Response.GetCarePlaneDetails;
import com.youro.web.repository.CarePlanRepository;
import com.youro.web.repository.PrescriptionRepository;
import com.youro.web.utils.HelpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarePlanService {

    @Autowired
    CarePlanRepository carePlanRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;


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


    public GetCarePlaneDetails getCarePlaneDetails(int apptId, int diagID)
    {
        if(apptId == 0 && diagID == 0)
        {
            throw new CustomException("Please provide appointment or diagnosis ID");
        }
        if(apptId == 0)
        {
            return getPrescriptionByDiagId(diagID);
        }
        else if (diagID == 0)
        {
            return getCarePlanByApptId(apptId);
        }
        else {
            return CarePlanMapper.getCarePlaneDetails(getPrescriptionByDiagId(diagID), getCarePlanByApptId(apptId));
        }
    }
}