package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "login_table")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int userId;

    @Column(unique = true, length = 30)
    public String email;

    @Column(name="password",length = 30)
    public String password;

    public UserType userType;

    @Column(name="first_name",length = 30)
    public String firstName;

    @Column(name="middle_name",length = 30)
    public String middleName;

    @Column(name="last_name",length = 30)
    public String lastName;

    public Gender gender;

    @Column(name="address",length = 50)
    public String address;

/*    @Column(name="address_2",length = 50)
    public String address2;*/

    @Column(length = 30)
    public String city;

    @Column(length = 10)
    public String state;

    @Column(name="zip_code",length = 30)
    public String zipCode;

    @Column(name="date_of_birth")
    @Temporal(TemporalType.DATE)
    public Date dateOfBirth;

    @Column(name = "phone_1",length = 12)
    public String phone1;

    @Column(name = "phone_2",length = 12)
    public String phone2;

    public Boolean hasInsurance;

    @Column(length = 20)
    public String relation;

    @Column(name="relation_email",length = 30)
    public String relationEmail;

    @Column(unique = true,length = 15)
    public String license;

    @Column(length = 40)
    public String specialization;

    public DoctorStatus status;

    public SubscriptionStatus subscriptionStatus;

    @Column(name="soft_delete")
    public Boolean softDelete;


}

