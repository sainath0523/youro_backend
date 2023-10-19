package com.youro.web.pojo.Response;

import java.util.Date;

public class GetSymptomScoreResponse {

    public String scoreId;
    public int patientId;
    public int diagnosisId;
    public double symptomScore;
    public String questionData;
    public Date dateTime;

    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(int diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public double getSymptomScore() {
        return symptomScore;
    }

    public void setSymptomScore(double symptomScore) {
        this.symptomScore = symptomScore;
    }

    public String getQuestionData() {
        return questionData;
    }

    public void setQuestionData(String questionData) {
        this.questionData = questionData;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
