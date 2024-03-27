package com.youro.web.repository;
import com.youro.web.entity.Diagnosis;
import com.youro.web.entity.Prescription;
import com.youro.web.entity.PrescriptionType;
import com.youro.web.entity.PreType;
import com.youro.web.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreTypeRepository extends JpaRepository<PreType, Integer> {

//    List<Prescription> findByPresTypeAndDiagnosis(PrescriptionType type, Diagnosis diag);
//
//    List<Prescription> findByPresTypeInAndDiagnosis(List<PrescriptionType> type, Diagnosis diag);
//
//    List<Prescription> findByDiagnosis(Diagnosis diag);
//    PreType findByName(String name);
}