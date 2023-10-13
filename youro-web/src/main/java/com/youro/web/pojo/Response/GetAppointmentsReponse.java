package com.youro.web.pojo.Response;

import com.youro.web.entity.AppointmentStatus;
import com.youro.web.entity.Appointments;

import java.util.Date;

public class GetAppointmentsReponse {

    public int apptId;
    public int patientId;
    public String doctorName;
    public String patientName;
    public int doctorId;
    public Date apptDate;

    public Date apptStartTime;
    public Date apptEndTime;
    public String link;
    public AppointmentStatus status;

    public int followUpId;

}
