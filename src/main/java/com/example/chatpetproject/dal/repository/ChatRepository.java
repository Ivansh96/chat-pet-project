package com.example.chatpetproject.dal.repository;


import com.example.chatpetproject.dal.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

    @Query("SELECT c FROM Chat c WHERE c.name LIKE %?1%")
    Optional<Chat> findByName(String name);

    Chat findChatById(UUID id);
}
