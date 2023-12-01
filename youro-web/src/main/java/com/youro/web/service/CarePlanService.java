package com.youro.web.service;

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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class CarePlanService {
	
	@Autowired
	NotificationService notificationService;
	
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
    
    /*public GetCarePlaneDetails getPrescriptionByDiagId(int diagId)
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
        }*//*
        else {
            return CarePlanMapper.getCarePlaneDetails(getPrescriptionByDiagId(diagID), getCarePlanByApptId(apptId));
        }*//*
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
    }*/

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
				if(carePlanList.isEmpty())
				{
					throw new CustomException("No Care Plan Exist");
				}
				carePlanList.sort(Comparator.comparing(CarePlan::getLastUpdated, Comparator.reverseOrder()));
				return getCarePlanById(carePlanList.get(0).getCarePlanId(), false);
			} else {
				Integer careplanID = carePlanRepository.findByUserId(uId);
				if(careplanID !=null) {
					return getCarePlanById(careplanID, false);
				}
				else
				{
					throw new CustomException("Care Plan ID does not Exits");
				}
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
		Appointments appt = HelpUtils.getAppointments(request.apptId);
		if(carePlanList.isEmpty()) {
			saveCheckList(appt, "ORDER");
			notificationService.saveCarePlanNotification(appt,carePlan, "NEW");

		}else
		{
			notificationService.saveCarePlanNotification(appt,carePlan, "EDIT");
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
			Appointments appt = appointmentsRepository.findById(appointments.getApptId()).get();
			if(check.equals("ORDER")) {
				checkList.orders = true;
				checkList.notes = false;
			}
			else if(check.equals("NOTES")) {
				checkList.notes = true;
				checkList.orders = false;
			}
			checkList.setPatientId(appt.getPatientId());
			checkList.setDoctorId(appt.getDoctorId());
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

	

	public List<GetCheckListResponse> getCheckList(int doctorId) {
        List<CheckList> checklists = checkListRepository.findByDoctorId(HelpUtils.getUser(doctorId));
		return CheckListMapper.entityToResponseMapping(checklists);
	}

}