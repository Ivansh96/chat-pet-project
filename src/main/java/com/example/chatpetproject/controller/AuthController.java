package com.example.chatpetproject.controller;


import com.example.chatpetproject.dal.entity.User;
import com.example.chatpetproject.service.RegistrationService;
import com.example.chatpetproject.util.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserValidator userValidator;
    private final RegistrationService registrationService;


    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        String name = "Ivan";
        return "registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        registrationService.registerUser(user);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        return "redirect:login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
