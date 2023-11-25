package com.youro.web.service;

import com.youro.web.entity.*;
import com.youro.web.mapper.*;
import com.youro.web.pojo.Request.AddAvailabilityRequest;
import com.youro.web.pojo.Request.SaveAppoitmentRequest;
import com.youro.web.pojo.Request.SymptomScoreRequest;
import com.youro.web.pojo.Response.*;
import com.youro.web.repository.*;
import com.youro.web.utils.HelpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    SymptomScoreRepository symptomScoreRepo;

    @Autowired
    ProviderService providerService;

    @Autowired
    NotificationService notificationService;


    @Autowired
    AppointmentsRepository appointmentsRepository;

    @Autowired
    DiagnosisRepository diagnosisRepository;

    @Autowired
    QuestionnairesRepository questionnairesRepository;
    @Autowired
    OptionsRepository optionsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DoctorScheduleRepository doctorScheduleRepository;
    
    @Autowired
    AmazonS3Service s3Service;
	
	public List<GetSymptomScoreResponse> getSymptomScore(int patientId){

        List<SymptomScore> res = symptomScoreRepo.findByPatientId(patientId);

        return SymptomScoreMapper.convertEntityToResPojo(res);
    }

    public GetAppointmentsResponse getAppointments(int uId, AppointmentStatus apptStatus, String timeZone) throws ParseException, IOException {
        List<Appointments> res = new ArrayList<>();

        User user = userRepository.findById(uId).get();

        if (apptStatus == null) {
            res.addAll(appointmentsRepository.findByUId(uId));
        } else {
            res.addAll(appointmentsRepository.findAppointments(uId, 1 ));
        }
        return AppointmentMapper.getAppointments(res, user.userType, userRepository, diagnosisRepository, symptomScoreRepo, timeZone, s3Service);
    }

    public List<GetDiagnosisByCustomerResponse> getDiagnosisByCustomer(int uId)
    {
        List<Integer> diagIDs = symptomScoreRepo.findByPatientIdDistinct(uId);
        List<GetDiagnosisByCustomerResponse> result = new ArrayList<>();
        diagIDs.forEach(id -> {
            GetDiagnosisByCustomerResponse resp = new GetDiagnosisByCustomerResponse();
            resp.diagId = id;
            resp.diagName = diagnosisRepository.findById(id).get().getName();
            result.add(resp);
        });
        return result;
    }



    public List<DiagnosisResponse> getAllDiagnoses(){
        List<Diagnosis> res = diagnosisRepository.findAll();
        return DiagnosisMapper.convertDiagnosisEntityToPojo(res);
    }
    public List<QuestionnairesResponse> getQuestionsByDiagId(int diagId){
        List<Questionnaires> res = questionnairesRepository.findByDiagnosisId(diagId);
        List<Options> options =optionsRepository.findByQuestionnairesIn(res);
        return QuestionnairesMapper.convertQuestionnairesEntityToPojo(res, options);
    }

    public SaveSymptomResponse saveSymptomScore(SymptomScoreRequest req){
        SaveSymptomResponse response = new SaveSymptomResponse();
        List<Integer> oIds = req.getQuestionData().stream()
                .flatMap(qData -> qData.optionsData.stream())
                .map(SymptomScoreRequest.optionsData::getOId)
                .toList();
        int score = optionsRepository.sumWeightsForOIds(oIds);
        SymptomScore sC = SymptomScoreMapper.convertReqBodyToEntity(req, score);
        symptomScoreRepo.save(sC);
        response.score = score;
        diagnosisRepository.findById(req.getDiagnosisId()).ifPresent(a -> response.diagName = a.getName());
        return response;
    }

    public List<GetCustomerAvailResponse> getAvailableSlotsByDate(String timeZone) throws ParseException {
        List<DoctorSchedule> output = doctorScheduleRepository.findDoctorAvailCustomer();
       return getSlots(output, timeZone);
    }

    public List<GetCustomerAvailResponse> getSlots(List<DoctorSchedule> output, String timeZone) throws ParseException {
         Map<Date, List<DoctorSchedule>> doctorsByStartDate = output.stream()
            .collect(Collectors.groupingBy(DoctorSchedule::getSchDate));
        Map<Date, List<SlotInfo>> result = getSlotInfo(doctorsByStartDate);
        return DoctorSchToSlotsMapper.getCustomerAvailResponse(result, timeZone);
    }

    public Map<Date, List<SlotInfo>> getSlotInfo(Map<Date, List<DoctorSchedule>> doctorsByStartDate) throws ParseException {
        Map<Date, List<SlotInfo>> res = new HashMap<>();
        for(Date date : doctorsByStartDate.keySet())
        {
            res.put(date,HelpUtils.getSlots(doctorsByStartDate.get(date)));
        }
        return res;
    }

    public BasicResponse saveAppointment(SaveAppoitmentRequest saveAppoitmentRequest) throws ParseException {

        BasicResponse resp = new BasicResponse();
        AddAvailabilityRequest request = new AddAvailabilityRequest();
        request.docId = saveAppoitmentRequest.docId;
        request.startTime = saveAppoitmentRequest.startTime;
        request.endTime = HelpUtils.addTime(saveAppoitmentRequest.startTime);
        Appointments appt = AppointmentMapper.saveAppointments(saveAppoitmentRequest,request.endTime);
        appointmentsRepository.save(appt);
        notificationService.saveApptNotification(appt.getDoctorId(),appt, "BOOKED");
        providerService.removeAvailability(request);
        userRepository.findById(request.docId).ifPresent(a -> resp.message = a.getFirstName() + " " + a.getLastName());
        return resp;
    }


}