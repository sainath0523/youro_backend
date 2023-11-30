package com.youro.web.service;

import com.youro.web.entity.*;
import com.youro.web.exception.CustomException;
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
import java.text.SimpleDateFormat;
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
    CarePlanRepository carePlanRepository;

    @Autowired
    DoctorScheduleRepository doctorScheduleRepository;
    
    @Autowired
    AmazonS3Service s3Service;

    SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzz)");


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
        List<Integer> apptIds = carePlanRepository.findDistinctAppointments();
        res.parallelStream().filter(appt -> apptIds.contains(appt.apptId)).forEach(appointments -> {
            appointments.status = AppointmentStatus.COMPLETED;
        });
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
        List<Integer> qIds = req.getQuestionData().stream().map(SymptomScoreRequest.questionData :: getQId).toList();
        List<Integer> oIds = req.getQuestionData().stream()
                .flatMap(qData -> qData.optionsData.stream())
                .map(SymptomScoreRequest.optionsData::getOId)
                .toList();
        int totalScore = questionnairesRepository.sumWeightsForQIds(qIds);
        int actualScore = optionsRepository.sumWeightsForOIds(oIds);
        double score = ((double) actualScore / (double) totalScore) * 100;
        double powerOfTen = Math.pow(10, 2);
        score=  Math.round(score * powerOfTen) / powerOfTen;
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
        Date startTime = inputFormat.parse(saveAppoitmentRequest.startTime);
        if(appointmentsRepository.hasCustomerConflict(startTime, saveAppoitmentRequest.patId) !=null)
        {
            throw new CustomException("An existing appointment already available for given slot");
        }
        List<Integer> doctorIds = doctorScheduleRepository.getAvailableDoctors(startTime);

        if(doctorIds.isEmpty()){
            throw new CustomException("All the slots for given time has been booked. Please book different time slot");
        }
        int docId = doctorIds.stream().findAny().get();
        AddAvailabilityRequest request = new AddAvailabilityRequest();
        request.docId = docId;
        request.startTime = saveAppoitmentRequest.startTime;
        request.endTime = HelpUtils.addTime(saveAppoitmentRequest.startTime);
        Appointments appt = AppointmentMapper.saveAppointments(saveAppoitmentRequest,request.endTime);
        providerService.removeAvailability(request);
        appointmentsRepository.save(appt);
        notificationService.saveApptNotification(appt.getDoctorId(),appt, "BOOKED");
        userRepository.findById(docId).ifPresent(a -> resp.message = a.getFirstName() + " " + a.getLastName());
        return resp;
    }


}