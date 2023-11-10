package com.youro.web.pojo.Response;

import java.util.ArrayList;
import java.util.List;


public class GetAppointmentsResponse {

    public List<AppointmentResponse> previousAppointments = new ArrayList<>();
    public List<AppointmentResponse> upComingAppointments = new ArrayList<>();


}