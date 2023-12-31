package com.example.chatpetproject.dal.repository;


import com.example.chatpetproject.dal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    User findUserById(UUID id);
}
