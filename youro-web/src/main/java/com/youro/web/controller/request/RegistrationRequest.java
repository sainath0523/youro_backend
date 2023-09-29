package com.youro.web.controller.request;

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
    public String firstName;

    @NotNull(message = "is required")
    public String lastName;

    public String userType;

    @NotNull(message = "is required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    public String email;

    @NotNull(message = "is required")
    public Boolean hasInsurance;

    public String relation;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    public String relatedEmail;

    @NotNull(message = "is required")
    public String password;
}
