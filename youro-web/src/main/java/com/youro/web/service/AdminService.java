package com.youro.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youro.web.entity.User;
import com.youro.web.entity.UserType;
import com.youro.web.exception.CustomException;
import com.youro.web.repository.UserRepository;

@Service
public class AdminService {
	
	@Autowired
    UserRepository userRepository;
	
	public User getDetailsById(String emailId) throws CustomException
    {
        Optional<User> user = userRepository.findByEmail(emailId);
        if(user.isEmpty())
        {
            throw new CustomException("No details found");
        }
        return user.get();
    }

    public List<User> getUsersByType(String uType)
    {
        UserType inpP = getUTypeEnumFromString(uType);
        return userRepository.findByUserType(inpP);
    }
    
    private UserType getUTypeEnumFromString(String uType){
        UserType inpP = UserType.PATIENT;
        if(uType.equals(UserType.PATIENT.toString())){
            inpP = UserType.PATIENT;
        }
        if(uType.equals(UserType.ADMIN.toString())){
            inpP = UserType.ADMIN;
        }
        if(uType.equals(UserType.PROVIDER.toString())){
            inpP = UserType.PROVIDER;
        }
        return inpP;
    }

}
