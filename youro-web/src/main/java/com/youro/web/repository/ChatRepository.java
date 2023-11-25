package com.youro.web.repository;

import com.youro.web.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

    @Modifying
    @Query(value = "UPDATE chat SET seen = 1 WHERE from_id = :from AND to_id = :to AND date_time < :dateTime", nativeQuery = true)
    void updateChat(@Param("from") Integer from, @Param("to") Integer to, @Param("dateTime") Date dateTime);

    @Query(value = "SELECT user_id, last_message FROM ( SELECT CASE WHEN to_id = ?1 THEN from_id ELSE to_id END AS user_id, CASE WHEN to_id = ?1 THEN to_id ELSE from_id END AS other_user_id, message AS last_message, date_time, ROW_NUMBER() OVER (PARTITION BY CASE WHEN to_id = ?1 THEN from_id ELSE to_id END, CASE WHEN to_id = ?1 THEN to_id ELSE from_id END ORDER BY date_time DESC) AS rn FROM  chat  WHERE  ?1 IN (to_id, from_id) ) AS subquery WHERE rn = 1 ORDER BY date_time DESC;", nativeQuery = true)
    List<Object[]> getChatHistory(int userID);

    @Query(value = "SELECT COUNT(*) FROM chat WHERE from_id = 10 AND to_id = 9 AND seen = 0", nativeQuery = true)
    Integer getUnSeenCount(int fromId, int toID);

    @Query(value = "Select * from chat where from_id IN (?1, ?2) and to_id IN(?1, ?2) ORDER BY date_time DESC", nativeQuery = true)
    List<Chat> getChatsByUserId(int userId, int userId2);

}