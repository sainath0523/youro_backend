package com.youro.web.repository;

import com.youro.web.entity.Appointments;
import com.youro.web.entity.CarePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarePlanRepository extends JpaRepository<CarePlan, Integer> {

    List<CarePlan> findByAppointments(Appointments appointments);

    @Query(value = "SELECT DISTINCT(c.appt_id), c.diagnosis_id FROM care_plan c JOIN appointment a ON a.appt_id = c.appt_id WHERE a.patient_id = ?1 ", nativeQuery = true)
    List<Integer[]> findByUserId(int uId);


}