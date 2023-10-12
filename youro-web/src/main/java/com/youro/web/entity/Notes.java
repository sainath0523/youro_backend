package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Table(name = "notes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int notesId;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private User patientId;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private User doctorId;

    @Temporal(TemporalType.TIME)
    public Date lastUpdated;
}
