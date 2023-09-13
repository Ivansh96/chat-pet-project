package com.example.chatpetproject.service;

import com.example.chatpetproject.dal.entity.User;
import com.example.chatpetproject.dal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(UUID id) {
        //todo check for null
        return userRepository.findUserById(id);
    }


}
