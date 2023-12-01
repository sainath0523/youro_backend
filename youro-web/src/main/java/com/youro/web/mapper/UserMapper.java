package com.youro.web.mapper;

import com.youro.web.entity.*;
import com.youro.web.pojo.Request.RegistrationRequest;
import com.youro.web.pojo.Request.UpdateUserRequest;
import com.youro.web.pojo.Response.GetNotificationsResponse;
import com.youro.web.pojo.Response.UserDetailsResponse;
import com.youro.web.service.AmazonS3Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

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

        if(requestBody.specialization != null) {
            userDetails.specialization = requestBody.specialization;
        }

        if(requestBody.middleName != null) {
            userDetails.middleName = requestBody.middleName;
        }

        userDetails.status = DoctorStatus.PENDING;
        userDetails.verified = false;
        userDetails.softDelete = false;


		return userDetails;
		
	}


    public static User updateRequestToUser(UpdateUserRequest requestBody, User existObj, PasswordEncoder passwordEncoder) {
        User userDetails = existObj;

        EnumSet<Gender> validGenders = EnumSet.allOf(Gender.class);
        EnumSet<UserType> validUserTypes = EnumSet.allOf(UserType.class);
        EnumSet<SubscriptionStatus> validSubscriptionStatus = EnumSet.allOf(SubscriptionStatus.class);
        EnumSet<DoctorStatus> validDocStatus = EnumSet.allOf(DoctorStatus.class);

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

        if(requestBody.softDelete != null) {
            userDetails.softDelete = requestBody.softDelete;
        }


        if(requestBody.verified != null) {
            userDetails.verified = requestBody.verified;
        }

        if (validDocStatus.contains(requestBody.status)) {
            userDetails.status = requestBody.status;
        }

        if (validUserTypes.contains(requestBody.userType)) {
            userDetails.userType = requestBody.userType;
        }
        if (validSubscriptionStatus.contains(requestBody.subscriptionStatus)) {
            userDetails.subscriptionStatus = requestBody.subscriptionStatus;
        }

        if(requestBody.middleName != null) {
            userDetails.middleName = requestBody.middleName;
        }

        if(requestBody.phone2 != null) {
            userDetails.phone2 = requestBody.phone2;
        }

        if(requestBody.specialization != null) {
            userDetails.specialization = requestBody.specialization;
        }

        return userDetails;

    }


	public static List<UserDetailsResponse> getUserDetails(List<User> users, AmazonS3Service s3Service) throws IOException {
        List<UserDetailsResponse> userDetails = new ArrayList<>();
        for(User user: users) {
        	UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        	userDetailsResponse.setAddress(user.getAddress());
        	userDetailsResponse.setCity(user.getCity());
        	userDetailsResponse.setDateOfBirth(user.getDateOfBirth());
        	userDetailsResponse.setEmail(user.getEmail());
        	userDetailsResponse.setFirstName(user.getFirstName());
        	userDetailsResponse.setGender(user.getGender());
        	userDetailsResponse.setHasInsurance(user.getHasInsurance());
        	userDetailsResponse.setLastName(user.getLastName());
        	userDetailsResponse.setLicense(user.getLicense());
        	userDetailsResponse.setPhone1(user.getPhone1());
        	userDetailsResponse.setRelation(user.getRelation());
        	userDetailsResponse.setRelationEmail(user.getRelationEmail());
        	userDetailsResponse.setSpecialization(user.getSpecialization());
        	userDetailsResponse.setState(user.getState());
        	userDetailsResponse.setStatus(user.getStatus());
        	userDetailsResponse.setSubscriptionStatus(user.getSubscriptionStatus());
        	userDetailsResponse.setUserId(user.getUserId());
        	userDetailsResponse.setVerified(user.getVerified());
        	userDetailsResponse.setZipCode(user.getZipCode());
        	userDetailsResponse.setProfilePicture(s3Service.getImage(user.getUserId()));
        	userDetails.add(userDetailsResponse);
        }
		return userDetails;
	}

}