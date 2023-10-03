package com.youro.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

public class SymptomScore {

    public String id;
    public String patientId;

    public String diagnosysId;

    public double symptomScore;
    public String questionData;

    @Column(name="date_of_birth",length = 30)
    @Temporal(TemporalType.TIMESTAMP)
    public Date dateOfBirth;
}
