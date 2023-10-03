package com.youro.web.entity;

import java.util.Date;

public class Appointments {


    public String id;
    public String patientId;

    public String doctorId;
    public Date date;

    public Date startTime;

    public Date endTime;

    public String link;

    public String followupId;

    public AppointmentStatus status;
}
