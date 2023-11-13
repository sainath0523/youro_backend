package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "care_plan")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int carePlanId;

    @ManyToOne
    @JoinColumn(name = "diagnosisId")
    private Diagnosis diagnosis;

    @ManyToOne
    @JoinColumn(name = "apptId")
    private Appointments appointments;

    @Temporal(TemporalType.TIMESTAMP)
    public Date lastUpdated;

    @ManyToOne
    @JoinColumn(name = "createdBy")
    public User createBy;

    public String followUp;

    @ManyToOne
    @JoinColumn(name = "lastUpdatedBy")
    public User lastUpdatedBy;

    @Lob
    @Column(columnDefinition = "TEXT")
    public String notes;
}