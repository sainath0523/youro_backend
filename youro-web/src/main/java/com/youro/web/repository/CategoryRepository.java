package com.youro.web.repository;
import com.youro.web.entity.Diagnosis;
import com.youro.web.entity.Prescription;
import com.youro.web.entity.PrescriptionType;
import com.youro.web.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

//    List<Prescription> findByPresTypeAndDiagnosis(PrescriptionType type, Diagnosis diag);
//
//    List<Prescription> findByPresTypeInAndDiagnosis(List<PrescriptionType> type, Diagnosis diag);
//
//    List<Prescription> findByDiagnosis(Diagnosis diag);
//    List<Prescription> findByPresType(PrescriptionType type);
}