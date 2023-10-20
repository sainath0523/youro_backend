package com.youro.web.repository;

import com.youro.web.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Integer> {


     @Query(value = "select * from doctor_schedule as dct where dct.doctor_id = ?1 and dct.date = ?2", nativeQuery = true)
     List<DoctorSchedule> findByDoctorIdAndSchDate(int id, String date);

}

