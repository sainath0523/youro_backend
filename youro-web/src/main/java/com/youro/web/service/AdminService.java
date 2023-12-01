package com.youro.web.service;

import com.youro.web.entity.*;
import com.youro.web.exception.CustomException;
import com.youro.web.mapper.PrescriptionMapper;
import com.youro.web.mapper.QuestionnairesMapper;
import com.youro.web.mapper.UserMapper;
import com.youro.web.pojo.Request.AddDiagnosisRequest;
import com.youro.web.pojo.Request.AddPrescriptionRequest;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.pojo.Response.PrescriptionDetails;
import com.youro.web.pojo.Response.QuestionnairesResponse;
import com.youro.web.pojo.Response.UserDetailsResponse;
import com.youro.web.repository.*;
import com.youro.web.utils.HelpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DiagnosisRepository diagnosisRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    QuestionnairesRepository questionnairesRepository;

    @Autowired
    OptionsRepository optionsRepository;
    
    @Autowired
    AmazonS3Service s3Service;

    public List<UserDetailsResponse> getUsersByType(UserType uType) throws IOException {
    	List<User> users = userRepository.findByUserType(uType);
    		return UserMapper.getUserDetails(users, s3Service);
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
        for(int diagId : request.diagnosisId) {
            Diagnosis diag = HelpUtils.getDiagnosis(diagId);
            List<Prescription> list = prescriptionRepository.findByPresTypeAndDiagnosis(request.type, diag);
            List<String> names = list.stream().map(Prescription::getName).toList();
            if (names.contains(request.name)) {
                throw new CustomException("Already exits in the records");
            }
            Prescription newPres = new Prescription();
            newPres.setName(request.name);
            newPres.setPresType(request.type);
            newPres.setDiagnosis(diag);
            prescriptionRepository.save(newPres);
        }

        return new BasicResponse("Added Successfully");
    }

    public List<Prescription> getAllPrescriptions(){
        return prescriptionRepository.findAll();
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

    public List<QuestionnairesResponse> getAllQuestionnaires(){
        List<Questionnaires> res = questionnairesRepository.findAll();
        List<Options> options =optionsRepository.findByQuestionnairesIn(res);
        return QuestionnairesMapper.convertQuestionnairesEntityToPojo(res, options);
    }



}