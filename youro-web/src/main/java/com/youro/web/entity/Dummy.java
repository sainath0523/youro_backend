package com.youro.web.entity;
import jakarta.persistence.*;

@Entity
@Table(name="dummy")
public class Dummy {

    @Id
    public String dum;
    public String sain;
}
