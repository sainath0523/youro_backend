package com.youro.web.repository;

import com.youro.web.entity.CarePlan;
import com.youro.web.entity.SymptomScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SymptomScoreRepository extends JpaRepository<SymptomScore, Integer> {

    @Query(value = "select * from symptom_score ss where ss.patient_id = ?1", nativeQuery=true)
    List<SymptomScore> findByPatientId( int patientId);
    
    @Query(value = "select * from symptom_score ss where ss.patient_id = ?1 and ss.diagnosis_id = ?2", nativeQuery=true)
    List<SymptomScore> findByPatientIdAndDiagId( int patientId, int diagId);

}
