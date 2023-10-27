package com.youro.web.pojo.Request;

import com.youro.web.entity.Gender;
import com.youro.web.entity.SubscriptionStatus;
import com.youro.web.entity.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
	
	@NotNull(message = "is required")
    public UserType userType;

    @NotNull(message = "is required")
    public String firstName;

    @NotNull(message = "is required")
    public String lastName;

    @NotNull(message = "is required")
    public String password;

    @NotNull(message = "is required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    public String email;

    public String dateOfBirth;
    
    @NotNull(message = "is required")
    public String phoneNumber;

    public Gender gender;
    
    public Boolean hasInsurance;

    public SubscriptionStatus subscriptionStatus;

    public String relation;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    public String relationEmail;
    
    public String license;


    public String address;


    public String state;


    public String city;


    public String zipCode;

}