package com.youro.web.repository;

import com.youro.web.entity.DoctorSchedule;
import com.youro.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.print.Doc;
import java.util.Date;
import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Integer> {


     @Query(value = "select * from doctor_schedule as dct where dct.doctor_id = ?1 and dct.date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 15 DAY)", nativeQuery = true)
     List<DoctorSchedule> findDoctorAvail(int id);

     @Query(value = "select * from doctor_schedule as dct where dct.doctor_id = ?1 and dct.date = ?2", nativeQuery = true)
     List<DoctorSchedule> findByDoctorIdAndSchDate(int id, String date);

     @Query(value = "select * from doctor_schedule as dct where dct.date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 15 DAY)", nativeQuery = true)
     List<DoctorSchedule> findDoctorAvailCustomer();


     @Query(value = "select * from doctor_schedule as dct where dct.doctor_id = ?1 AND dct.date = ?2", nativeQuery = true)

     List<DoctorSchedule> findDoctorAvailability(int docId);

}

