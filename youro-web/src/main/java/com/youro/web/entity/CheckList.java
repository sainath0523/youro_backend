package com.youro.web.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    public Boolean followUp;
    
    public Boolean notes;
    public Boolean orders;

}
