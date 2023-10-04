package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "questionnaires")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Questionnaires {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int qId;

    public String ans;
    public String question;

    public QuestionType qType;

    @ManyToOne
    @JoinColumn(name = "diagnosisId")
    private Diagnosis diagnosis;
}
