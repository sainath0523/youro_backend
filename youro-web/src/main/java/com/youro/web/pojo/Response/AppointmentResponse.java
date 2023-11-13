package com.youro.web.pojo.Response;

import com.youro.web.entity.AppointmentStatus;

public class AppointmentResponse {

    public int apptId;
    public int patientId;
    public String doctorName;
    public String patientName;

    public byte[] picture;

    public int doctorId;
    public String apptDate;
    public String apptStartTime;
    public String apptEndTime;
    public String link;
    public AppointmentStatus status;
    
    public int diagId;
    public String diagName;
    public double symptomScore;
    public String dateOfGeneratedScore;
}