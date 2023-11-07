package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "notes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int notesId;

    @ManyToOne
    @JoinColumn(name = "patientId")
    public User patientId;

    @ManyToOne
    @JoinColumn(name = "apptId")
    public Appointments appointments;

    @Lob
    @Column(columnDefinition = "TEXT")
    public String notes;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    public User doctorId;

    @Temporal(TemporalType.TIMESTAMP)
    public Date lastUpdated;
}