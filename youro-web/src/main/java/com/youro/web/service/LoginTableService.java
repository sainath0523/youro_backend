package com.youro.web.service;

import com.youro.web.controller.request.LoginRequest;
import com.youro.web.controller.request.RegistrationRequest;
import com.youro.web.controller.response.BasicResponse;
import com.youro.web.entity.LoginTable;
import com.youro.web.entity.UserType;
import com.youro.web.exception.CustomException;
import com.youro.web.repository.LoginTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoginTableService {

    @Autowired
    LoginTableRepository loginTableRepository;

    public BasicResponse login(LoginRequest requestBody) throws CustomException
    {
        BasicResponse resp = new BasicResponse();
        Optional<LoginTable> user = loginTableRepository.findById(requestBody.username);
        if(user.isPresent())
        {
            if(!user.get().password.equals(requestBody.password))
            {
                throw new CustomException("Password Incorrect");
            }
        }
        else
        {
            throw new CustomException("User not Available");
        }

        resp.message = "Login Success";
        return resp;
    }

    public BasicResponse register(RegistrationRequest requestBody) throws CustomException
    {
        BasicResponse resp = new BasicResponse();
        Optional<LoginTable> user = loginTableRepository.findById(requestBody.email);
        if(requestBody.relatedEmail !=null && !requestBody.email.isEmpty())
        {
            Optional<LoginTable> userRelated = loginTableRepository.findById(requestBody.relatedEmail);
            if(userRelated.isEmpty())
            {
                throw new CustomException("No Account found with " + requestBody.email + " email ID");
            }
        }
        if(user.isEmpty())
        {
            LoginTable userDetails = LoginTable.builder().firstName(requestBody.firstName)
                    .lastName(requestBody.lastName)
//                    .userType(UserType.valueOf(requestBody.userType))
                    .email(requestBody.email)
                    .hasInsurance(requestBody.hasInsurance)
                    .relation(requestBody.relation)
                    .relationEmail(requestBody.relatedEmail)
                    .password(requestBody.password).build();
            loginTableRepository.save(userDetails);
        }
        else
        {
            throw new CustomException("Account exists with provided Email ID");
        }

        resp.message = "Login Success";
        return resp;
    }

    public List<LoginTable> get()
    {
        return loginTableRepository.findAll();
    }
    
    public Optional<LoginTable> getById(String userId)
    {
        return loginTableRepository.findById(userId);
    }


}
