package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Table(name = "symptom_score")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SymptomScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int scoreId;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private LoginTable patientId;

    @ManyToOne
    @JoinColumn(name = "diagnosisId")
    private Diagnosis diagnosis;

    public double symptomScore;

    public String questionData;

    @Temporal(TemporalType.TIMESTAMP)
    public Date dateTime;
}
