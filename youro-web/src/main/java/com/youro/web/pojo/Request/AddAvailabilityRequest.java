package com.youro.web.pojo.Request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAvailabilityRequest {

    public int docId;
    public String startTime;
    public String endTime;

}
