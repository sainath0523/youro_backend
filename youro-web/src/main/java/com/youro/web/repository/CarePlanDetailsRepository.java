package com.youro.web.repository;

import com.youro.web.entity.CarePlan;
import com.youro.web.entity.CarePlanDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarePlanDetailsRepository extends JpaRepository<CarePlanDetails, Integer> {

    List<CarePlanDetails> findByCarePlan(CarePlan carePlan);
}