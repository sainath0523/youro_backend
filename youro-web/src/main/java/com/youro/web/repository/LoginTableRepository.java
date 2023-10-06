package com.youro.web.repository;

import com.youro.web.entity.LoginTable;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginTableRepository extends JpaRepository<LoginTable, String> {
    Optional<LoginTable> findByEmail(String email);
}
