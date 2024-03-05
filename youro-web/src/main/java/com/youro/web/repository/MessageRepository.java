package com.youro.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.youro.web.entity.Chat;

public interface MessageRepository extends JpaRepository<Chat, Integer> {

}
