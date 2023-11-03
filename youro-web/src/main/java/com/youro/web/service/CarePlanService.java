package com.youro.web.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import com.youro.web.bucket.BucketName;
import com.youro.web.entity.Results;
import com.youro.web.entity.User;
import com.youro.web.exception.CustomException;
import com.youro.web.pojo.Response.BasicResponse;
import com.youro.web.repository.ResultsRepository;
import com.youro.web.repository.UserRepository;
import com.youro.web.utils.HelpUtils;
import com.youro.web.entity.*;
import com.youro.web.mapper.CarePlanMapper;
import com.youro.web.mapper.NotesMapper;
import com.youro.web.pojo.Request.SaveCarePlanRequest;
import com.youro.web.pojo.Request.SaveNotesRequest;
import com.youro.web.pojo.Response.*;
import com.youro.web.repository.*;

import java.util.Comparator;
import java.util.Date;


@Service
public class CarePlanService {
	
	
	@Autowired
	AmazonS3 s3;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ResultsRepository resultsRepository;
	
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

	public BasicResponse uploadResults(MultipartFile[] files, int patientId) {
		
		if(files.length == 0)
			throw new IllegalStateException("No files to upload");
		
		try {
			String bucketName = String.format("%s", BucketName.PROFILE_IMAGE.getBucketName());
			String rootPath = String.format("%s", Integer.toString(patientId));
			this.save(bucketName, rootPath, files);
			this.saveResultsUrl(patientId, bucketName, rootPath);
			return new BasicResponse("Files uploaded to s3 successfully");
		}
		catch(IOException e) {
			throw new IllegalStateException("Failed to store file to s3", e); 
		}
		
	}

	public void save(String bucketName, String rootPath, MultipartFile[] files) throws IOException {
		try {
			for (MultipartFile mFile: files) {
				File file = convertFile(mFile);
				String filePath = String.format("%s/%s", rootPath, file.getName());
				s3.putObject(bucketName, filePath, file);
			}
			System.out.println("succesfully uploaded files");			
		}
		catch(AmazonServiceException e){
			throw new CustomException("Failed to upload files to S3");
		}
	}
	
	private void saveResultsUrl(int patientId, String bucketName, String rootPath) {
		String results_url = String.format("%s/%s", bucketName, rootPath);

		Optional<User> user = userRepository.findByUserId(patientId);
		if(user.isEmpty()) {
            throw new CustomException("No user found with id: " + patientId);
		}
		Optional<Results> results = resultsRepository.findByPatientId(user.get());
		if(results.isEmpty()) {
			Results res = new Results();
			res.setPatientId(HelpUtils.getUser(patientId));
			res.setResults_url(results_url);
            res.setLastUpdated(new Date());
			resultsRepository.save(res);		
		}
		else {
			Results res = results.get();
            res.results_url = results_url;
            res.setLastUpdated(new Date());
            resultsRepository.save(res);
		}
				
	}
	
	public static File convertFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	public List<byte[]> getResults(int patientId) {
		Optional<User> user = userRepository.findByUserId(patientId);
		if(user.isEmpty()) {
            throw new CustomException("No user found with id: " + patientId);
		}
		Optional<Results> results = resultsRepository.findByPatientId(user.get());
		if(results.isEmpty()) {
            throw new CustomException("No results for patient: " + patientId);
		}
		String results_url = results.get().getResults_url();	
		return getResultsFromS3(results_url);
	}

	private List<byte[]> getResultsFromS3(String results_url) {
		try {
			String[] parts = results_url.split("/");
			String bucketName = parts[0];
		    String rootPath = parts[1];
		    
	        List<byte[]> resultList = new ArrayList<>();
	        ObjectListing objectListing = s3.listObjects(bucketName, rootPath);
	        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
	        
	        for (S3ObjectSummary objectSummary : objectSummaries) {
	            S3Object obj = s3.getObject(bucketName, objectSummary.getKey());
	            S3ObjectInputStream inputStream = obj.getObjectContent();
	            byte[] data = IOUtils.toByteArray(inputStream);
                resultList.add(data);
	        }
	        return resultList;			
		}
		catch(AmazonServiceException | IOException e) {
			throw new CustomException("Failed to get files from S3");
		}
	}

}