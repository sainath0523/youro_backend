package com.youro.web.repository;

import com.youro.web.entity.Diagnosis;
import com.youro.web.entity.Prescription;
import com.youro.web.entity.PrescriptionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

    List<Prescription> findByPresTypeAndDiagnosis(PrescriptionType type, Diagnosis diag);

    List<Prescription> findByPresType(PrescriptionType type);
}