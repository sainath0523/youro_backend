package com.youro.web.mapper;

import com.youro.web.entity.AppointmentStatus;
import com.youro.web.entity.Appointments;
import com.youro.web.entity.Diagnosis;
import com.youro.web.entity.SymptomScore;
import com.youro.web.entity.User;
import com.youro.web.entity.UserType;
import com.youro.web.pojo.Request.SaveAppoitmentRequest;
import com.youro.web.pojo.Response.AppointmentResponse;
import com.youro.web.pojo.Response.GetAppointmentsResponse;
import com.youro.web.repository.DiagnosisRepository;
import com.youro.web.repository.SymptomScoreRepository;
import com.youro.web.repository.UserRepository;
import com.youro.web.utils.HelpUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AppointmentMapper {
	
	static SimpleDateFormat  timeFormat = new SimpleDateFormat("HH:mm:ss");
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzz)");
    
    public static GetAppointmentsResponse getAppointments(List<Appointments> response, UserType userType, UserRepository userRepository, DiagnosisRepository diagnosisRepository, SymptomScoreRepository symptomScoreRepository)
    {
        GetAppointmentsResponse resp = new GetAppointmentsResponse();
        List<AppointmentResponse> previous = new ArrayList<>();
        List<AppointmentResponse> upComing = new ArrayList<>();
        Date date = new Date();
        for(Appointments app : response)
        {
            AppointmentResponse res = new AppointmentResponse();
            String patPrefix = app.getPatientId().getGender() == null ? "" : (app.getPatientId().getGender().toString().equals("MALE") )  ? "Mr. " : "Ms. ";
            String docPrefix = app.getDoctorId().getGender() == null ? "" : (app.getDoctorId().getGender().toString().equals("MALE") )  ? "Mr. " : "Ms. ";
            res.apptId = app.apptId;
            res.apptDate =dateFormat.format(app.apptDate);
            res.apptStartTime= timeFormat.format(app.apptStartTime) ;
            res.apptEndTime = timeFormat.format(app.apptEndTime);
            res.link = app.link;
            res.doctorId = app.getDoctorId().userId;
            res.patientId = app.getPatientId().userId;
            res.doctorName = docPrefix + app.getDoctorId().firstName + " " + app.getDoctorId().lastName;
            res.patientName = patPrefix + app.getPatientId().firstName + " " + app.getPatientId().lastName;
            res.status = app.getStatus();
            res.diagId = app.getDiagnosis().getDiagId();           
            Diagnosis diag = diagnosisRepository.findById(res.diagId).get();
            res.diagName = diag.getName();
            Map.Entry<Double, Date> symptomScoreEntry = getSymptomScore(res.diagId, app.apptStartTime, symptomScoreRepository);
            res.symptomScore = symptomScoreEntry.getKey();
            res.dateOfGeneratedScore = dateFormat.format(symptomScoreEntry.getValue());       
            
            User user = null;
            if(userType ==  UserType.PATIENT)
            {
                user = userRepository.findById(res.patientId).get();
            }
            else {
                user = userRepository.findById(res.doctorId).get();
            }
            res.picture = user.profilePicture;
            if(app.apptDate.before(date)) {
                previous.add(res);
            }
            else
            {
                upComing.add(res);
            }
        }
        resp.previousAppointments = previous;
        resp.upComingAppointments = upComing;
        return resp;
    }

    private static Map.Entry<Double, Date> getSymptomScore(int diagId, Date apptStartTime, SymptomScoreRepository symptomScoreRepository) {
        List<SymptomScore> symptomScores = symptomScoreRepository.findByDiagId(diagId);
        Optional<SymptomScore> result = symptomScores.stream()
                .filter(symptomScore -> symptomScore.getDateTime().before(apptStartTime))
                .max(Comparator.comparing(SymptomScore::getDateTime));

        if (result.isPresent()) {
            return new AbstractMap.SimpleEntry<>(result.get().getSymptomScore(), result.get().getDateTime());
        } 
        else {
            return new AbstractMap.SimpleEntry<>(null, null);
        }

	}

	public static Appointments saveAppointments(SaveAppoitmentRequest request, String endTime) throws ParseException {
        Appointments appt = new Appointments();
        appt.status = AppointmentStatus.SCHEDULED;
        appt.setDoctorId(HelpUtils.getUser(request.docId));
        appt.setPatientId(HelpUtils.getUser(request.patId));
        appt.setApptDate(outputFormat.parse(request.startTime));
        appt.setApptStartTime(outputFormat.parse(request.startTime));
        appt.setDiagnosis(HelpUtils.getDiagnosis(request.getDiagId()));
        appt.setApptEndTime(outputFormat.parse(endTime));
        return appt;
    }
}