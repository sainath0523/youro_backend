package com.youro.web.repository;

import com.youro.web.entity.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentsRepository extends JpaRepository<Appointments, String> {

    @Query(value = "select * from appointment as appt where appt.patient_id = ?1 and appt.status = ?2", nativeQuery=true)
    List<Appointments> findByPatientIdAndStatus(int uId, int status);

    @Query(value = "select * from appointment as appt where appt.doctor_id = ?1 and appt.status = ?2", nativeQuery=true)
    List<Appointments> findByDoctorIdAndStatus(int uId, int status);

    @Query(value = "select * from appointment as appt where appt.patient_id = ?1", nativeQuery=true)
    List<Appointments> findByPatientId(int uId);

    @Query(value = "select * from appointment as appt where appt.doctor_id = ?1", nativeQuery=true)
    List<Appointments> findByDoctorId(int uId);

}
