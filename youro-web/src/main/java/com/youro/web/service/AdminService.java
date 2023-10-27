package com.youro.web.service;

import com.youro.web.entity.*;
import com.youro.web.exception.CustomException;
import com.youro.web.mapper.PrescriptionMapper;
import com.youro.web.pojo.Request.AddDiagnosisRequest;
import com.youro.web.pojo.Request.AddPrescriptionRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Response.PrescriptionDetails;
import com.youro.web.repository.DiagnosisRepository;
import com.youro.web.repository.PrescriptionRepository;
import com.youro.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DiagnosisRepository diagnosisRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;

    public List<User> getUsersByType(UserType uType) {
        return userRepository.findByUserType(uType);
    }

    public BasicResponse addDiagnosis(AddDiagnosisRequest request)
    {

        BasicResponse response = new BasicResponse();
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.name = request.name;
        diagnosis.info = request.info;
        diagnosisRepository.save(diagnosis);

        response.message = "Added Successfully";

        return response;
    }

    public BasicResponse addPrescription(AddPrescriptionRequest request)
    {
        BasicResponse response = new BasicResponse();
        Diagnosis diag = new Diagnosis();
        diag.setDiagId(request.diagnosisId);
        List<Prescription> list = prescriptionRepository.findByPresTypeAndDiagnosis(request.type, diag);
        List<String> names = list.stream().map(Prescription :: getName).toList();
        if(names.contains(request.name))
        {
            throw new CustomException("Already exits in the records");
        }
        response.message ="Added successfully";
        return response;
    }


    public List<PrescriptionDetails> getPrescriptionDetailsByType(PrescriptionType type)
    {
        List<Prescription> list = prescriptionRepository.findByPresType(type);
        return PrescriptionMapper.convertPrescription(list);
    }

    public BasicResponse deletePrescription(int id)
    {
        prescriptionRepository.deleteById(id);
        BasicResponse resp = new BasicResponse();
        resp.message = "Deleted Successfully";
        return resp;
    }


}