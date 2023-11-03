package com.youro.web.repository;

import com.youro.web.entity.Appointments;
import com.youro.web.entity.CheckList;
import com.youro.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheckListRepository extends JpaRepository<CheckList, Integer> {

	List<CheckList> findByDoctorId(User doctor);

	Optional<CheckList> findByAppointments(Appointments appointments);

}