package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prescription")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int presId;

    public String name;

    public PrescriptionType presType;

    @ManyToOne
    @JoinColumn(name = "diagnosisId")
    private Diagnosis diagnosis;

    @ManyToOne
    @JoinColumn(name = "catId")
    private Category category;

//    @ManyToOne
//    @JoinColumn(name = "presType")
//    private PreType preType;


    public String shortInfo;

    @Lob
    public String overview;
}