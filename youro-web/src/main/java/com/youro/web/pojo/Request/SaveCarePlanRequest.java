package com.youro.web.pojo.Request;

import com.youro.web.pojo.Response.GetCarePlaneDetails;
import lombok.Getter;

@Getter
public class SaveCarePlanRequest {

    public int apptId;
    public int diagID;

    public String notes;

    public int doctorId;
    public GetCarePlaneDetails carePlanDetails;
}