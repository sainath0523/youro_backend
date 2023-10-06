package com.youro.web.conversion;

import java.util.EnumSet;

import org.springframework.stereotype.Component;

import com.youro.web.controller.request.RegistrationRequest;
import com.youro.web.entity.LoginTable;
import com.youro.web.entity.SubscriptionStatus;
import com.youro.web.entity.UserType;
import com.youro.web.entity.Gender;

@Component
public class LoginTableConversion {
	
	public LoginTable toLoginTable(RegistrationRequest requestBody) {
		LoginTable userDetails = new LoginTable();
		
        EnumSet<Gender> validGenders = EnumSet.allOf(Gender.class);
        EnumSet<UserType> validUserTypes = EnumSet.allOf(UserType.class);
        EnumSet<SubscriptionStatus> validSubscriptionStatus = EnumSet.allOf(SubscriptionStatus.class);

        if (validGenders.contains(requestBody.gender)) {
        	userDetails.gender = requestBody.gender;
        }
        
        if (validUserTypes.contains(requestBody.userType)) {
        	userDetails.userType = requestBody.userType;
        }
        
        if (validSubscriptionStatus.contains(requestBody.subscriptionStatus)) {
        	userDetails.subscriptionStatus = requestBody.subscriptionStatus;
        }
        
        if(requestBody.firstName != null) {
        	userDetails.firstName = requestBody.firstName;
        }
        
        if(requestBody.lastName != null) {
            userDetails.lastName = requestBody.lastName;        
        }
        
        if(requestBody.email != null) {
            userDetails.email = requestBody.email;
        }
        
        if(requestBody.password != null) {
            userDetails.password = requestBody.password;
        }
        
        if(requestBody.hasInsurance != null) {
            userDetails.hasInsurance = requestBody.hasInsurance;
        }
        
        if(requestBody.relation != null) {
            userDetails.relation = requestBody.relation;
        }
        
        if(requestBody.relationEmail != null) {
            userDetails.relationEmail = requestBody.relationEmail;
        }
        
        if(requestBody.dateOfBirth != null) {
            userDetails.dateOfBirth = requestBody.dateOfBirth;
        }
        
        if(requestBody.address != null) {
            userDetails.address = requestBody.address;
        }
        
        if(requestBody.city != null) {
            userDetails.city = requestBody.city;
        }
        
        if(requestBody.state != null) {
            userDetails.state = requestBody.state;
        }
        
        if(requestBody.zipCode != null) {
            userDetails.zipCode = requestBody.zipCode;
        }
        
        if(requestBody.phoneNumber != null) {
            userDetails.phone1 = requestBody.phoneNumber;
        }
        
        if(requestBody.license != null) {
            userDetails.license = requestBody.license;
        }
        
		return userDetails;
		
	}

}
