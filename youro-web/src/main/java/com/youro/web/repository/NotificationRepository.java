package com.youro.web.repository;

import com.youro.web.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query(value = "select * from notification where user_id = ?1 ORDER BY date_time DESC", nativeQuery = true)
    List<Notification> getByUser(int userId);
}