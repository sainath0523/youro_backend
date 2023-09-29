package com.youro.web.service;

import com.youro.web.entity.LoginTable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoginTableService {

    public List<LoginTable> get();
}
