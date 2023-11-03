package com.youro.web.repository;

import com.youro.web.entity.User;
import com.youro.web.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByUserType(UserType userType);
	Optional<User> findByUserId(int uId);
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM user as usr WHERE usr.email = ?1) AS value_exists;", nativeQuery = true)
    int checkIfEmailExists(String email);

}
