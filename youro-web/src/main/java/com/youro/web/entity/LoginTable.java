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


    @Column(name="user_name",unique = true,length = 30)
    public String userName;
    @Id
    @Column(unique = true, length = 30)
    public String email;
    @Column(name="password",length = 30)
    public String password;

    public UserType userType;
    @Column(name="first_name",length = 30)
    public String firstName;
    @Column(name="last_name",length = 30)
    public String lastName;
    @Column(name="address_1",length = 50)
    public String address1;
    @Column(name="address_2",length = 50)
    public String address2;
    @Column(length = 30)
    public String city;
    @Column(length = 10)
    public String state;
    @Column(name="pin_code",length = 30)
    public String pinCode;
    @Column(name="date_of_birth",length = 30)
    @Temporal(TemporalType.DATE)
    public Date dateOfBirth;

    @Column(length = 12)
    public String phone1;
    @Column(length = 12)
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
    @Column(name="soft_delete")
    public Boolean softDelete;




}
