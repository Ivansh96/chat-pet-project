package com.example.chatpetproject.dal.repository;


import com.example.chatpetproject.dal.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

    @Query("select c from Chat c where c.name like %?1%")
    Optional<Chat> findByName(String name);

    Chat findChatById(UUID id);

    @Query(nativeQuery = true,
            value = "SELECT c.id, c.name, c.user_id FROM users u JOIN chat c ON u.id = c.user_id WHERE c.user_id = :userId"
                    )
    List<Chat> findAllByUserId(@Param("userId") UUID userId);
}
