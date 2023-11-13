package com.youro.web.repository;

import com.youro.web.entity.CarePlan;
import com.youro.web.entity.Questionnaires;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionnairesRepository extends JpaRepository<Questionnaires, String> {
    @Query(value = "select * from questionnaires as ques where (ques.diagnosis_id = ?1)", nativeQuery=true)
    List<Questionnaires> findByDiagnosisId(int diagId);
}
