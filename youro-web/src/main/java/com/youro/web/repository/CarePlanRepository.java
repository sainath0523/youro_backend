package com.youro.web.repository;

import com.youro.web.entity.CarePlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarePlanRepository extends JpaRepository<CarePlan, String> {
}
