package com.youro.web.mapper;

import com.youro.web.entity.Appointments;
import com.youro.web.pojo.Response.GetAppointmentsReponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppointmentMapper {
    public static List<GetAppointmentsReponse> getAppointments(List<Appointments> response)
    {
        List<GetAppointmentsReponse> responses = new ArrayList<>();

        for(Appointments app : response)
        {
            GetAppointmentsReponse res = new GetAppointmentsReponse();

            String patPrefix = app.getPatientId().getGender().toString().equals("MALE") ? "Mr. " : "Ms. ";
            String docPrefix = app.getDoctorId().getGender().toString().equals("MALE") ? "Mr. " : "Ms. ";
            res.apptId = app.apptId;
            res.apptDate =app.apptDate;
            res.apptStartTime= app.apptStartTime ;
            res.apptEndTime = app.apptEndTime;
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
}
