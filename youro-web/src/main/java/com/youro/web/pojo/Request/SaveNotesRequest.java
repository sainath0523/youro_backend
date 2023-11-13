package com.youro.web.pojo.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveNotesRequest {

    public int apptId;
    public int doctorId;
    public int patientId;
    public String notes;
}