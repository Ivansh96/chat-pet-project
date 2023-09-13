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


    @GetMapping("/chats")
    public String getAllChats(@AuthenticationPrincipal UserResponse user, Model model) {
        List<Chat> chatList = user.getUser().getChats();
        System.out.println();
        model.addAttribute("chatList", chatList);
        return "chats";
    }

    @GetMapping("/addNew")
    public String addChat(@ModelAttribute("chat") Chat chat) {
        return "newChat";
    }

        @PostMapping("/saveChat")
    public String addNewChat(@AuthenticationPrincipal UserResponse user,
                             @ModelAttribute("chat") Chat chat,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "newChat";
        }
        User user1 = user.getUser();
        chatService.addChat(chat, user1);
        return "redirect:chats";
    }


    @GetMapping("/chats/{id}")
    public String getMessagesForChat(@PathVariable("id") UUID id, Model model) {
        String description = null;
        Chat chat = chatService.findById(id);
        for (int i = 0; i < chat.getMessages().size(); i++) {
            description = chat.getMessages().get(i).getDescription();
        }
        model.addAttribute("message", description);
        return "messages";
    }

    @GetMapping("/addNewMessage/{id}")
    public String addMessage(@ModelAttribute("message") Message message) {
        return "newMessage";
    }

    @PostMapping("/saveMessage/{id}")
    public String addNewMessage(@ModelAttribute("message") Message message,
                                @PathVariable("id") UUID id,
                                BindingResult result) {

        if (result.hasErrors()) {
            return "newMessage";
        }
        chatService.addMessage(message, id);
        return "redirect:/getMessages/{id}";
    }


    @GetMapping("/deleteChat/{id}")
    public String deleteChatById(@PathVariable("id") UUID id) {
        chatService.deleteChatById(id);
        return "redirect:chats";
    }

    @GetMapping("/deleteMessage/{id}")
    public String deleteMessageById(@PathVariable("id") UUID id) {
        chatService.deleteMessageById(id);
        return "redirect:/";
    }
}
