package com.youro.web.repository;

import com.youro.web.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, String> {
    List<DoctorSchedule> findBySchDate(Date date);
}
