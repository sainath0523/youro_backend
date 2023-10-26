package com.youro.web.pojo.Response;

import com.youro.web.entity.AppointmentStatus;


public class GetAppointmentsResponse {

    public int apptId;
    public int patientId;
    public String doctorName;
    public String patientName;
    public int doctorId;
    public String apptDate;
    public String apptStartTime;
    public String apptEndTime;
    public String link;
    public AppointmentStatus status;

    public int followUpId;


}
