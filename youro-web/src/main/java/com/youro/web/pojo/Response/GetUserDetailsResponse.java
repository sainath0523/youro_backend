package com.youro.web.pojo.Response;

import com.youro.web.entity.DoctorStatus;
import com.youro.web.entity.Gender;
import com.youro.web.entity.SubscriptionStatus;
import com.youro.web.entity.UserType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GetUserDetailsResponse {

    public int userId;

    public String email;

    public UserType userType;

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
}