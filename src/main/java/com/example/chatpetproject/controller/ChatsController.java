package com.example.chatpetproject.controller;

import com.example.chatpetproject.dal.entity.Chat;
import com.example.chatpetproject.dal.entity.Message;
import com.example.chatpetproject.dal.entity.User;
import com.example.chatpetproject.security.UserResponse;
import com.example.chatpetproject.service.ChatService;
import com.example.chatpetproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatsController {

    private final ChatService chatService;
    private final UserService userService;


    @GetMapping("/chats")
    public String getAllChats(Principal principal, Model model) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        List<Chat> chatList = chatService.findAllByUserId(user.getId());
        model.addAttribute("chatList", chatList);
        model.addAttribute("user", user);
        return "chats";
    }

    @GetMapping("/addNew/{userId}")
    public String addChat(@ModelAttribute("chat") Chat chat,
                          @PathVariable("userId") UUID userId,
                          Model model) {
        model.addAttribute("userId", userId);
        return "newChat";
    }

    @PostMapping("/saveChat/{userId}")
    public String addNewChat(@PathVariable("userId") UUID userId,
                             @ModelAttribute("chat") Chat chat,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "newChat";
        }
        User user = userService.findById(userId);
        chatService.addChat(chat, user);
        return "redirect:/chats";
    }

    @GetMapping("/chats/{id}")
    public String getMessagesForChat(@ModelAttribute("chat") Chat chat, Model model) {

        List<Message> messages = chat.getMessages();
        model.addAttribute("messages", messages);
        model.addAttribute("chat", chat);
        return "messages";
    }

    @GetMapping("/addNewMessage/{chatId}")
    public String addMessage(@ModelAttribute("message") Message message,
                             @PathVariable("chatId") UUID chatId,
                             Model model) {
        model.addAttribute("chatId", chatId);
        return "newMessage";
    }

    @PostMapping("/messages/{chatId}")
    public String addNewMessage(@ModelAttribute("message") Message message,
                                @PathVariable("chatId") UUID chatId,
                                BindingResult result) {


        if (result.hasErrors()) {
            return "newMessage";
        }
        chatService.addMessage(message, chatId);
        return "redirect:/chats";
    }


    @GetMapping("/deleteChat/{id}")
    public String deleteChatById(@PathVariable("id") UUID id) {
        chatService.deleteChatById(id);
        return "redirect:/chats";
    }

    @GetMapping("/deleteMessage/{id}")
    public String deleteMessageById(@PathVariable("id") UUID id) {
        chatService.deleteMessageById(id);
        return "redirect:/";
    }
}
