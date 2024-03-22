package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prescription_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PreType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int presTypeId;

    public String name;

    //public PrescriptionType presType;
}