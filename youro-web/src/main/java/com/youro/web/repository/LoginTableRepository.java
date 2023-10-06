package com.youro.web.repository;

import com.youro.web.entity.LoginTable;
import com.youro.web.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoginTableRepository extends JpaRepository<LoginTable, String> {

    List<LoginTable> findByUserType(UserType userType);

    Optional<LoginTable> findByEmail(String email);

}
