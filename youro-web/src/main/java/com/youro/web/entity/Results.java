package com.youro.web.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "results")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Results {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int resultsId;

    @ManyToOne
    @JoinColumn(name = "patientId")
    public User patientId;

    @Column(columnDefinition = "TEXT")
    public String results_url;

    @Temporal(TemporalType.TIMESTAMP)
    public Date lastUpdated;
}
