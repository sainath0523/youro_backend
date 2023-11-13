package com.youro.web.repository;

import com.youro.web.entity.DoctorSchedule;
import com.youro.web.entity.Options;
import com.youro.web.entity.Questionnaires;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OptionsRepository extends JpaRepository<Options, Integer> {

    List<Options> findByQuestionnairesIn(List<Questionnaires> questionnaires);

    @Query("SELECT SUM(o.weight) FROM Options o WHERE o.oId IN :oIds")
    Integer sumWeightsForOIds(@Param("oIds") List<Integer> oIds);
}
