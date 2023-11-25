package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "notification")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int notId;

    public String message;

    @ManyToOne
    @JoinColumn(name = "userId")
    public User userId;

    @Temporal(TemporalType.TIMESTAMP)
    public Date dateTime;
}