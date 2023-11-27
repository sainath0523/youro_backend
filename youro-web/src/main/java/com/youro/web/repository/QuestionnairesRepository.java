package com.youro.web.repository;

import com.youro.web.entity.Questionnaires;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionnairesRepository extends JpaRepository<Questionnaires, String> {
    @Query(value = "select * from questionnaires as ques where (ques.diagnosis_id = ?1)", nativeQuery=true)
    List<Questionnaires> findByDiagnosisId(int diagId);

    @Query(value = "SELECT SUM(weight) from questionnaires  WHERE q_Id IN :qIds", nativeQuery = true)
    Integer sumWeightsForQIds(@Param("qIds") List<Integer> qIds);
}