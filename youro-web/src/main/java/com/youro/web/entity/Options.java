package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "options")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int oId;

    @ManyToOne
    @JoinColumn(name = "qId")
    public Questionnaires questionnaires;

    public String optionName;

    public int weight;

}
