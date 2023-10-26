package com.youro.web.repository;

import com.youro.web.entity.DoctorSchedule;
import com.youro.web.entity.Options;
import com.youro.web.entity.Questionnaires;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionsRepository extends JpaRepository<Options, Integer> {

    List<Options> findByQuestionnairesIn(List<Questionnaires> questionnaires);
}
