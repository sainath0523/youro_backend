package com.youro.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.youro.web.entity.CheckList;
import com.youro.web.entity.User;

public interface CheckListRepository extends JpaRepository<CheckList, Integer> {

	List<CheckList> findByDoctorId(User doctor);

}
