package com.youro.web.mapper;

import com.youro.web.entity.Gender;
import com.youro.web.entity.SubscriptionStatus;
import com.youro.web.entity.User;
import com.youro.web.entity.UserType;
import com.youro.web.pojo.Request.RegistrationRequest;
import com.youro.web.pojo.Request.UpdateUserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.EnumSet;

@Component
public class UserMapper {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public User toUser(RegistrationRequest requestBody, PasswordEncoder passwordEncoder) throws ParseException {
		User userDetails = new User();
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
            userDetails.password = passwordEncoder.encode(requestBody.password);
            System.out.println("length of encoded password: "+ userDetails.password.length());
            System.out.println("encoded password: "+ userDetails.password);
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
            userDetails.dateOfBirth = dateFormat.parse(requestBody.dateOfBirth);
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


    public static User updateRequestToUser(UpdateUserRequest requestBody, PasswordEncoder passwordEncoder) {
        User userDetails = new User();

        EnumSet<Gender> validGenders = EnumSet.allOf(Gender.class);
//        EnumSet<UserType> validUserTypes = EnumSet.allOf(UserType.class);
//        EnumSet<SubscriptionStatus> validSubscriptionStatus = EnumSet.allOf(SubscriptionStatus.class);

        if (validGenders.contains(requestBody.gender)) {
            userDetails.gender = requestBody.gender;
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
            userDetails.password = passwordEncoder.encode(requestBody.password);
            System.out.println("length of encoded password: "+ userDetails.password.length());
            System.out.println("encoded password: "+ userDetails.password);
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

        if(requestBody.phone1 != null) {
            userDetails.phone1 = requestBody.phone1;
        }

        if(requestBody.license != null) {
            userDetails.license = requestBody.license;
        }

        return userDetails;

    }

}