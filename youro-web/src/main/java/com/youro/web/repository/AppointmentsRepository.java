package com.youro.web.repository;

import com.youro.web.entity.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {

    @Query(value = "select * from appointment as appt where appt.patient_id = ?1 or appt.doctor_id = ?1", nativeQuery = true)
    List<Appointments> findByUId(int uId);

    @Query(value = "select * from appointment as appt where appt.doctor_id = :uId AND appt.appt_start_time >= :dateTime AND appt.appt_end_time <= :endTime", nativeQuery = true)
    List<Appointments> getDeleteAppointments(@Param("uId") Integer uId, @Param("dateTime") Date startTime, @Param("endTime") Date endTime);

    @Query(value = "select * from appointment as appt where (appt.patient_id = ?1 or appt.doctor_id = ?1) and appt.status = ?2", nativeQuery = true)
    List<Appointments> findAppointments(int uId, int status);

    @Query(value = "select * from appointment as appt where (appt.patient_id = ?1 or appt.doctor_id = ?1) and appt.status = 1 and appt.appt_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 15 DAY)", nativeQuery = true)
    List<Appointments> findAppointments(int uId);

    @Query(value = "SELECT a.appt_id FROM appointment a WHERE a.appt_start_time = :givenStartTime AND a.patient_id = :patID AND a.status = 1 ", nativeQuery = true)
    Integer hasCustomerConflict(
            @Param("givenStartTime") Date givenStartTime,
            @Param("patID") Integer patID
    );
   /* @Query(value = "SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM appointment a , doctor_schedule d " +
            "WHERE " +
            "   (a.appt_start_time = :givenStartTime AND a.doctor_id = :docID AND a.status = 1)) " +
            "   OR " +
            "   (:givenStartTime NOT BETWEEN d.start_time AND d.end_time AND d.doctor_id = :docID)", nativeQuery = true)
    boolean hasConflict(
            @Param("givenStartTime") Date givenStartTime,
            @Param("docID") Integer docID
    );*/

}