package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int categoryId;

    public String name;

    //public PrescriptionType presType;
}