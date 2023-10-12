package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "appointment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Appointments {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int apptId;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private User patientId;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private User doctorId;

    @Temporal(TemporalType.DATE)
    public Date apptDate;

    @Temporal(TemporalType.TIME)
    public Date apptStartTime;

    @Temporal(TemporalType.TIME)
    public Date apptEndTime;


    public String link;

    @ManyToOne
    @JoinColumn(name = "followupId", referencedColumnName = "apptId")
    public Appointments followupId;

    public AppointmentStatus status;
}
