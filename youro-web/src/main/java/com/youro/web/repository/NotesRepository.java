package com.youro.web.repository;

import com.youro.web.entity.Notes;
import com.youro.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Integer> {


    List<Notes> findByPatientId(User user);
}