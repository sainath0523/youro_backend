package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GetCarePlanResponse {


    public String diagName;
    public String diagInfo;

    public String notes;
    public String followUp;

    public Date lastModified;
    public GetCarePlaneDetails carePlan;



}