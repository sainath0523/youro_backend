package com.youro.web.service;

import com.youro.web.entity.*;
import com.youro.web.exception.CustomException;
import com.youro.web.mapper.PrescriptionMapper;
import com.youro.web.mapper.QuestionnairesMapper;
import com.youro.web.mapper.UserMapper;
import com.youro.web.pojo.Request.AddDiagnosisRequest;
import com.youro.web.pojo.Request.AddPrescriptionRequest;
import com.youro.web.pojo.Request.AddCategoryRequest;
import com.youro.web.pojo.Request.EditCategoryRequest;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DiagnosisRepository diagnosisRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    QuestionnairesRepository questionnairesRepository;

    @Autowired
    OptionsRepository optionsRepository;
    
    @Autowired
    AmazonS3Service s3Service;

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

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
                throw new CustomException("Already exists in the records");
            }
            Prescription newPres = new Prescription();
            newPres.setName(request.name);
            newPres.setPresType(request.type);
            newPres.setDiagnosis(diag);
            prescriptionRepository.save(newPres);
        }

        return new BasicResponse("Added Successfully");
    }

    public BasicResponse addCategory(AddCategoryRequest request)
    {
        List<Category> list = categoryRepository.findAll();
        List<String> names = list.stream().map(Category::getName).toList();
        if (names.contains(request.name)) {
            throw new CustomException("Already exists in the records");
        }
        Category newCat = new Category();
        newCat.setName(request.name);
        categoryRepository.save(newCat);

        logger.info("edfvdjnscvdc");
        return new BasicResponse("Added Successfully");
    }


    public BasicResponse editCategory(int catId, EditCategoryRequest request) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new CustomException("Category not found"));
        //console.log("Inside the Update Category Backend", category);
        //System.out.println("Inside the Update Category Backend", category);
        logger.info("Found category: {} - Now updating name to: {}", category.getName(), request.getName());
        category.setName(request.getName());
        categoryRepository.save(category);

        logger.info("Category updated successfully: {} - {}", catId, request.getName());
        return new BasicResponse("Category updated successfully");
    }

    public List<Prescription> getAllPrescriptions(){
        return prescriptionRepository.findAll();
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
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

//    public BasicResponse deleteCategory(int id)
//    {
//        categoryRepository.deleteById(id);
//        BasicResponse resp = new BasicResponse();
//        resp.message = "Deleted Successfully";
//        return resp;
//    }

    public BasicResponse deleteCategory(int id) {
        try {
            categoryRepository.deleteById(id);
            logger.info("Category deleted successfully: {}", id);
        } catch (DataIntegrityViolationException e) {
            // This exception is thrown when the category cannot be deleted due to database constraints (e.g., foreign key constraints)
            logger.error("Failed to delete category due to database constraints: {}", id, e);
            throw new CustomException("Failed to delete category, as it is referenced elsewhere.");
        } catch (Exception e) {
            // Handle other unexpected exceptions
            logger.error("Unexpected error occurred when attempting to delete category: {}", id, e);
            throw new CustomException("An unexpected error occurred.");
        }

        return new BasicResponse("Deleted Successfully");
    }

    //wergifdbkosadhnfgbdsdmhgfndbf

    public List<QuestionnairesResponse> getAllQuestionnaires(){
        List<Questionnaires> res = questionnairesRepository.findAll();
        List<Options> options =optionsRepository.findByQuestionnairesIn(res);
        return QuestionnairesMapper.convertQuestionnairesEntityToPojo(res, options);
    }



}