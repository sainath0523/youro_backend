package com.youro.web.service.impl;

import com.youro.web.entity.LoginTable;
import com.youro.web.repository.LoginTableRepository;
import com.youro.web.service.LoginTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginTableServiceImpl implements LoginTableService {

    @Autowired
    LoginTableRepository loginTableRepository;
    @Override
    public List<LoginTable>  get() {
        return loginTableRepository.findAll();
    }
}
