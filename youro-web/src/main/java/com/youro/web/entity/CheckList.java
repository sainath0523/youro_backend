package com.youro.web.entity;

import jakarta.persistence.*;

public class CheckList {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int checkId;

    @OneToOne
    @JoinColumn(name = "apptId")
    public Appointments appointments;

    public Boolean followUp;
    public Boolean notes;
    public Boolean orders;

}
