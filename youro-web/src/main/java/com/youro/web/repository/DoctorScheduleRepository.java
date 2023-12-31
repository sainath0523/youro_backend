package com.youro.web.repository;

import com.youro.web.entity.DoctorSchedule;
import com.youro.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Integer> {


     @Query(value = "select * from doctor_schedule as dct where dct.doctor_id = ?1 and dct.date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 15 DAY)", nativeQuery = true)
     List<DoctorSchedule> findDoctorAvail(int id);

     //@Query(value = "select * from doctor_schedule as dct where dct.doctor_id = ?1 and dct.date = ?2", nativeQuery = true)
     List<DoctorSchedule> findByDoctorIdAndSchDate(User id, Date date);

     @Query(value = "select * from doctor_schedule as dct where dct.date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 15 DAY)", nativeQuery = true)
     List<DoctorSchedule> findDoctorAvailCustomer();


     @Query(value = "select * from doctor_schedule as dct where dct.doctor_id = ?1 AND dct.date = ?2", nativeQuery = true)
     List<DoctorSchedule> findDoctorAvailability(int docId);

     @Modifying
     @Query(value = "DELETE from doctor_schedule WHERE doctor_id = :doctor AND start_time >= :startTime AND end_time <= :endTime ", nativeQuery = true)
     void getDoctor(@Param("doctor") Integer doctor, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

     @Query(value = "SELECT d.doctor_id FROM doctor_schedule d WHERE :givenStartTime BETWEEN d.start_time AND d.end_time ", nativeQuery = true)
     List<Integer> getAvailableDoctors(
             @Param("givenStartTime") Date givenStartTime
     );

     @Query(value = "select * from  doctor_schedule WHERE doctor_id = :doctor AND (:startTime > start_time AND :startTime < end_time\n" +
             "    OR :endTime > start_time AND :endTime < end_time\n" +
             "    OR start_time > :startTime AND start_time < :endTime\n" +
             "    OR end_time > :startTime AND end_time < :endTime)", nativeQuery = true)
     List<DoctorSchedule> getDoctorList(@Param("doctor") Integer doctor, @Param("startTime") Date startTime, @Param("endTime") Date endTime);



}