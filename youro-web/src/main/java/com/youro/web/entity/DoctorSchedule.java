package com.youro.web.entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "doctor_schedule")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int schId;
    @ManyToOne
    @JoinColumn(name = "doctorId")
    private LoginTable doctorId;

    public DrSchdStatus status;

    @Column(name="date")
    @Temporal(TemporalType.DATE)
    public Date schDate;

    @Column(name="startTime")
    @Temporal(TemporalType.TIME)
    public Date schStartTime;

    @Column(name="endTime")
    @Temporal(TemporalType.TIME)
    public Date schEndTime;

}
