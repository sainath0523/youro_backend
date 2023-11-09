package com.youro.web.pojo.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveAppoitmentRequest {

    public int docId;
    public int patId;
    public String startTime;
    public int diagId;

}