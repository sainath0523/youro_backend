package com.youro.web.repository;

import com.youro.web.entity.CarePlan;
import com.youro.web.entity.CarePlanDetails;
import com.youro.web.entity.Prescription;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarePlanDetailsRepository extends JpaRepository<CarePlanDetails, Integer> {

    List<CarePlanDetails> findByCarePlan(CarePlan carePlan);

    List<CarePlanDetails> findByPrescription(Prescription prescription);
}