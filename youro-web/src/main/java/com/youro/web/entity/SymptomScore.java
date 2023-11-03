package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "symptom_score")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SymptomScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int scoreId;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private User patientId;

    @ManyToOne
    @JoinColumn(name = "diagnosisId")
    private Diagnosis diagnosis;

    public double symptomScore;

    @Lob
    public String questionData;

    @Temporal(TemporalType.TIMESTAMP)
    public Date dateTime;
}