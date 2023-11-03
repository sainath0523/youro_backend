package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "appointment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @Temporal(TemporalType.TIMESTAMP)
    public Date apptStartTime;

    @Temporal(TemporalType.TIMESTAMP)
    public Date apptEndTime;

    public String link;

    public AppointmentStatus status;
}