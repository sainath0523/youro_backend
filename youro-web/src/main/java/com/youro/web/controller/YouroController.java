package com.youro.web.controller;

import com.youro.web.entity.LoginTable;
import com.youro.web.service.LoginTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class YouroController {

    @Autowired
    LoginTableService loginTableService;
    @GetMapping("/getDetails")
    public List<LoginTable> getDetails()
    {
        return loginTableService.get();
    }

}
