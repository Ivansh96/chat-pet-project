package com.example.chatpetproject.controller;

import com.example.chatpetproject.dal.entity.Chat;
import com.example.chatpetproject.dal.entity.Message;
import com.example.chatpetproject.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatsController {

    private final ChatService chatService;

    @GetMapping("/chats")
    public String getAllChats(Model model) {
        List<Chat> chats = chatService.findAllChats();
        model.addAttribute("chatList", chats);
        return "chats";
    }

    @GetMapping("/addNew")
    public String addChat(@ModelAttribute("chat") Chat chat) {
        return "new";
    }

    @PostMapping("/saveChat")
    public String addNewChat(@ModelAttribute("chat") Chat chat, BindingResult result) {
        if (result.hasErrors()) {
            return "new";
        }
        chatService.addChat(chat);
        return "redirect:/";
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////


    @GetMapping("/chats/{id}")
    public String getMessagesForChat(@PathVariable("id") UUID id, Model model) {
        String desc = null;
        Chat chat = chatService.findById(id);
        for (int i = 0; i < chat.getMessages().size(); i++) {
            desc = chat.getMessages().get(i).getDescription();
        }
        model.addAttribute("message", desc);
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


    ///////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/deleteChat/{id}")
    public String deleteChatById(@PathVariable("id") UUID id) {
        chatService.deleteChatById(id);
        return "redirect:/";
    }

    @GetMapping("/deleteMessage/{id}")
    public String deleteMessageById(@PathVariable("id") UUID id) {
        chatService.deleteMessageById(id);
        return "redirect:/";
    }
}
