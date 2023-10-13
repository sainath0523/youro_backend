package com.youro.web.pojo.Request;

import java.util.Date;

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

    @NotNull(message = "is required")
    public Date dateOfBirth;
    
    @NotNull(message = "is required")
    public String phoneNumber;

    @NotNull(message = "is required")
    public Gender gender;
    
    public Boolean hasInsurance;

    public SubscriptionStatus subscriptionStatus;

    public String relation;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    public String relationEmail;
    
    public String license;

    @NotNull(message = "is required")
    public String address;

    @NotNull(message = "is required")
    public String state;

    @NotNull(message = "is required")
    public String city;

    @NotNull(message = "is required")
    public String zipCode;

}