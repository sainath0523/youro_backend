package com.youro.web.repository;

import com.youro.web.entity.Appointments;
import com.youro.web.entity.CarePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarePlanRepository extends JpaRepository<CarePlan, Integer> {

    List<CarePlan> findByAppointments(Appointments appointments);
}