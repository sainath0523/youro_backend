package com.youro.web.pojo.Request;

import com.youro.web.entity.DoctorStatus;
import com.youro.web.entity.Gender;
import com.youro.web.entity.SubscriptionStatus;

import java.util.Date;

public class UpdateUserRequest {

    public String firstName;

    public String middleName;

    public String lastName;

    public Gender gender;

    public String address;

    public String city;

    public String state;

    public String zipCode;
    public Date dateOfBirth;

    public String phone1;

    public String phone2;

    public Boolean hasInsurance;

    public String relation;

    public String relationEmail;


    public String license;

    public String specialization;

    public DoctorStatus status;

    public SubscriptionStatus subscriptionStatus;

    public Boolean verified;

    public Boolean softDelete;

    public byte[] profilePicture;
}
