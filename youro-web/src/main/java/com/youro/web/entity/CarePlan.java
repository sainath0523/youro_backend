package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "care_plan")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int carePlanId;

    public PrescriptionType presType;

    @ManyToOne
    @JoinColumn(name = "diagnosisId")
    private Diagnosis diagnosis;

    @ManyToOne
    @JoinColumn(name = "presId")
    private Prescription presId;

    @Lob
    public String dosage;

    @ManyToOne
    @JoinColumn(name = "apptId")
    private Appointments appointments;

    @Temporal(TemporalType.TIMESTAMP)
    public Date lastUpdated;
}