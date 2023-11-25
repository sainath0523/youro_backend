package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GetCheckListResponse {

//    public int doctorId;
//    public int patientId;
//    public int apptId;
    
    public Date apptDate;
    public String patientName;

    public Boolean orders;
    public Boolean notes;

}