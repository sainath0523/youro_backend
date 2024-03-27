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

    @ManyToOne
    @JoinColumn(name = "presType")
    public PrescriptionType presType;

    @ManyToOne
    @JoinColumn(name = "diagnosisId")
    private Diagnosis diagnosis;

    @ManyToOne
    @JoinColumn(name = "catId")
    private Category category;


   // private PreType preType;


    public String shortInfo;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    public String overview;
}