package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class DoctorAvailabilityResponse {

    List<GetAppointmentsResponse> appoitments;

    List<DoctorAvailability> docAvail;

    public static class DoctorAvailability
    {

        public int doctorId;
        public String startTime;
        public String endTime;

        public String status;


    }

}
