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
public class PrescriptionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int presTypeId;

    public String name;

    //public PrescriptionType presType;
}

//public enum PrescriptionType {
//    LAB,
//    VITAMINS,
//    MEDICINES,
//    IMAGING,
//    LIFESTYLE,
//    MEDIA
//}

