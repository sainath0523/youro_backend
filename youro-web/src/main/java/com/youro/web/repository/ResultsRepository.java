package com.youro.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.youro.web.entity.Results;
import com.youro.web.entity.User;

public interface ResultsRepository extends JpaRepository<Results, String> {

	Optional<Results> findByPatientId(User user);

}
