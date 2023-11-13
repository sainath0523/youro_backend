package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarePlanDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int cId;

    @ManyToOne
    @JoinColumn(name = "careId")
    public CarePlan carePlan;

    @ManyToOne
    @JoinColumn(name = "presId")
    public Prescription prescription;

    @Lob
    @Column(columnDefinition = "TEXT")
    public String dosage;
}