package com.youro.web.mapper;

import com.youro.web.entity.AppointmentStatus;
import com.youro.web.entity.Appointments;
import com.youro.web.entity.User;
import com.youro.web.pojo.Request.SaveAppoitmentRequest;
import com.youro.web.pojo.Response.GetAppointmentsResponse;
import com.youro.web.pojo.Response.GetAppointmentsResponse;
import com.youro.web.utils.Constants;
import com.youro.web.utils.HelpUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AppointmentMapper {

    static SimpleDateFormat  timeFormat = new SimpleDateFormat("HH:mm:ss");
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzz)");
    public static List<GetAppointmentsResponse> getAppointments(List<Appointments> response)
    {
        List<GetAppointmentsResponse> responses = new ArrayList<>();

        for(Appointments app : response)
        {
            GetAppointmentsResponse res = new GetAppointmentsResponse();

            String patPrefix = app.getPatientId().getGender().toString().equals("MALE") ? "Mr. " : "Ms. ";
            String docPrefix = app.getDoctorId().getGender().toString().equals("MALE") ? "Mr. " : "Ms. ";
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
            if(app.followupId != null)
            {
                res.followUpId = app.followupId.getApptId();
            }
            responses.add(res);

        }
        return responses;
    }

    public static Appointments saveAppointments(SaveAppoitmentRequest request) throws ParseException {
        Appointments appt = new Appointments();
        appt.status = AppointmentStatus.SCHEDULED;
        appt.setDoctorId(HelpUtils.getUser(request.docId));
        appt.setPatientId(HelpUtils.getUser(request.patId));
        appt.setApptDate(Constants.dateFormat.parse(request.startTime));
        appt.setApptStartTime(Constants.timeFormat.parse(request.startTime));
        appt.setApptEndTime(Constants.timeFormat.parse(request.endTime));
        return appt;

    }
}
