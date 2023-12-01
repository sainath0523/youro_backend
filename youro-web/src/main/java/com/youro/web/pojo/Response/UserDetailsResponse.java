package com.youro.web.pojo.Response;

import java.util.Date;

import com.youro.web.entity.DoctorStatus;
import com.youro.web.entity.Gender;
import com.youro.web.entity.SubscriptionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {
	int userId;
	String email;
	String firstName;
	String lastName;
	Gender gender;
	String address;
	String city;
	String state;
	String zipCode;
	Date dateOfBirth;
	String phone1;
	public Boolean hasInsurance;
	String relation;
	String relationEmail;
	SubscriptionStatus subscriptionStatus;
	Boolean verified;
	byte[] profilePicture;
	String license;
	String specialization;
	DoctorStatus status;
}
