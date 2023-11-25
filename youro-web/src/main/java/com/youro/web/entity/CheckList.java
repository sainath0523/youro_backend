package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "checkList")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckList {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int checkId;

    @OneToOne
    @JoinColumn(name = "apptId")
    public Appointments appointments;
    
    @Temporal(TemporalType.DATE)
    public Date apptDate;
    
    @ManyToOne
    @JoinColumn(name = "doctorId")
    private User doctorId;
    
    @ManyToOne
    @JoinColumn(name = "patientId")
    private User patientId;
    
    public Boolean notes;
    public Boolean orders;

}