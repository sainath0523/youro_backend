package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class GetCarePlanByPatientResponse {

    public int apptId;
    public Date apptDate;

    public int diagId;
    public String diagnosisName;
}