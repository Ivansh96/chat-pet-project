package com.example.chatpetproject.service;


import com.example.chatpetproject.dal.entity.Chat;
import com.example.chatpetproject.dal.entity.Message;
import com.example.chatpetproject.dal.entity.User;
import com.example.chatpetproject.dal.repository.ChatRepository;
import com.example.chatpetproject.dal.repository.MessageRepository;
import com.example.chatpetproject.dal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public Chat findById(UUID id) {
        Optional<Chat> chat = chatRepository.findById(id);
        return chat.orElseThrow();
    }

    public List<Chat> findAllByUserId(UUID userId) {
        return chatRepository.findAllByUserId(userId);
    }

    public void addChat(Chat chat, User user) {
        user.getChats().add(chat);
        userRepository.save(user);
    }

    public void addMessage(Message message, UUID chatId) {
        Chat chat = chatRepository.findChatById(chatId);
        chat.getMessages().add(message);
        chatRepository.save(chat);
    }

    public void deleteChatById(UUID id) {
        chatRepository.deleteById(id);
    }

//    public void deleteMessageById(UUID id) {
//        messageRepository.deleteById(id);
//    }
}
