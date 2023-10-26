package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GetSymptomScoreResponse {

    public int scoreId;
    public int patientId;
    public int diagnosisId;
    public double symptomScore;
    public String questionData;
    public Date dateTime;

}
