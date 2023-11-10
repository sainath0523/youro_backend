package com.youro.web.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import com.youro.web.bucket.BucketName;
import com.youro.web.entity.*;
import com.youro.web.exception.CustomException;
import com.youro.web.mapper.CarePlanMapper;
import com.youro.web.mapper.CheckListMapper;
import com.youro.web.mapper.NotesMapper;
import com.youro.web.pojo.Request.SaveCarePlanRequest;
import com.youro.web.pojo.Request.SaveNotesRequest;
import com.youro.web.pojo.Response.*;
import com.youro.web.repository.*;
import com.youro.web.utils.HelpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


@Service
public class CarePlanService {
	
	
	@Autowired
	AmazonS3 s3;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ResultsRepository resultsRepository;
	
	@Autowired
	CheckListRepository checkListRepository;
	
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

	@Autowired
	CarePlanDetailsRepository carePlanDetailsRepository;
    
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
        //return CarePlanMapper.mapCarePlanEntity(carePlanList);
		return new GetCarePlaneDetails();
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
        }/*
        else {
            return CarePlanMapper.getCarePlaneDetails(getPrescriptionByDiagId(diagID), getCarePlanByApptId(apptId));
        }*/
		return  new GetCarePlaneDetails();
    }

    public List<GetCarePlanByPatientResponse> getCarePlanByPatient(int uId)
    {
        List<GetCarePlanByPatientResponse> responses = new ArrayList<>();
        List<Integer[]> carePlanIDs = carePlanRepository.findByUserIdVersions(uId);
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

	public List<GetCarePlanVersions> getCarePlanVersions(int apptId)
	{
		List<CarePlan> carePlanList = carePlanRepository.findByAppointments(HelpUtils.getAppointments(apptId));
		carePlanList.sort(Comparator.comparing(CarePlan :: getLastUpdated, Comparator.reverseOrder()));
		return CarePlanMapper.mapCarePlanVersions(carePlanList);
	}

	public GetCarePlanResponse getCarePlanById(int cId, Boolean bool)
	{
		List<CarePlanDetails> carePlanDetails = carePlanDetailsRepository.findByCarePlan(HelpUtils.getCarePlan(cId));
		CarePlan carePlan = carePlanRepository.findById(cId).get();
		List<Prescription> prescriptionList = new ArrayList<>();
		if(bool)
		{
			prescriptionList = prescriptionRepository.findByDiagnosis(HelpUtils.getDiagnosis(carePlan.getDiagnosis().getDiagId()));
		}
		return CarePlanMapper.mapCarePlanResponse(carePlanDetails, carePlan, prescriptionList);
	}

	public GetCarePlanResponse getCarePlanByPatient(Integer uId, Integer apptId)
	{

		if ((uId != null && apptId == null) || (apptId != null && uId == null)) {
			List<CarePlan> carePlanList = new ArrayList<>();
			if (apptId != null) {
				carePlanList = carePlanRepository.findByAppointments(HelpUtils.getAppointments(apptId));
				carePlanList.sort(Comparator.comparing(CarePlan::getLastUpdated, Comparator.reverseOrder()));
				return getCarePlanById(carePlanList.get(0).getCarePlanId(), false);
			} else {
				int careplanID = carePlanRepository.findByUserId(uId);
				return getCarePlanById(careplanID, false);
			}
		}
		else
		{
			throw new CustomException("Only one Request Param is allowed");
		}
	}

	public GetCarePlaneDetails getCarePlaneDetailsById(int diagId)
	{
		List<Prescription> prescriptionList = prescriptionRepository.findByDiagnosis(HelpUtils.getDiagnosis(diagId));
		return CarePlanMapper.getCarePlaneByPrescription(prescriptionList);

	}

    public List<GetNotesResponse> getNotes(int uId)
    {
        List<Notes> notes = notesRepository.findByPatientId(HelpUtils.getUser(uId));
        return NotesMapper.entityToResponseMapping(notes);
    }

    public BasicResponse saveNotes(SaveNotesRequest request)
    {
        notesRepository.save(NotesMapper.requestToEntityMapper(request));
		Appointments appt = HelpUtils.getAppointments(request.apptId);
		saveCheckList(appt, "NOTES");
        return new BasicResponse("Notes Saved Successfully");
    }

    public BasicResponse saveCarePlan(SaveCarePlanRequest request)
    {
		List<CarePlan> carePlanList = carePlanRepository.findByAppointments(HelpUtils.getAppointments(request.getApptId()));
		CarePlan carePlan = carePlanRepository.save(CarePlanMapper.toCarePlanEntity(request, carePlanList));
		carePlanDetailsRepository.saveAll(CarePlanMapper.toCarePlanDetailsEntity(request.getCarePlanDetails(), carePlan));
		if(carePlanList.isEmpty()) {
			Appointments appt = HelpUtils.getAppointments(request.apptId);
			saveCheckList(appt, "ORDER");
		}
        return new BasicResponse("CarePlan Saved Successfully");
    }

	public void saveCheckList(Appointments appointments, String check)
	{
		CheckList checkList = new CheckList();
		Optional<CheckList> checkRecord = checkListRepository.findByAppointments(appointments);
		if(checkRecord.isEmpty())
		{
			checkList.setAppointments(appointments);
			if(check.equals("ORDER")) {
				checkList.orders = true;
			}
			else if(check.equals("NOTES")) {
				checkList.notes = true;
			}
			checkList.apptDate = appointments.getApptDate();
		}
		else
		{
			checkList = checkRecord.get();
			if(check.equals("ORDER")) {
				checkList.orders = true;
			}
			else if(check.equals("NOTES")) {
				checkList.notes = true;
			}
		}
		checkListRepository.save(checkList);
	}

	public BasicResponse uploadResults(MultipartFile[] files, int patientId) {
		
		if(files.length == 0)
			throw new CustomException("No files to upload");
		try {
			String bucketName = String.format("%s", BucketName.PROFILE_IMAGE.getBucketName());
			String rootPath = String.format("%s", Integer.toString(patientId));
			this.save(bucketName, rootPath, files);
			this.saveResultsUrl(patientId, bucketName, rootPath);
			return new BasicResponse("Files uploaded to s3 successfully");
		}
		catch(IOException e) {
			throw new CustomException(e.getLocalizedMessage());
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

	public List<GetCheckListResponse> getCheckList(int doctorId) {
        List<CheckList> checklists = checkListRepository.findByDoctorId(HelpUtils.getUser(doctorId));
		return CheckListMapper.entityToResponseMapping(checklists);
	}

}