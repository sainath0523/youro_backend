package com.youro.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youro.web.entity.User;
import com.youro.web.service.AdminService;

@RestController
@RequestMapping("/youro/api/v1/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
    @PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getUser/{email}")
    public User getAllUsers(@PathVariable("email") String email)
    {
        return adminService.getDetailsById(email);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllUsers/{type}")
    public List<User> getUsersByType(@PathVariable("type") String uType) {
        return adminService.getUsersByType(uType);
    }


}
