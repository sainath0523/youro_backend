package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GetNotesResponse {

    public Date apptTime;
    public String doctorName;
    public String patientName;
    public String notes;
    public Date lastUpdated;
}