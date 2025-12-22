package com.spring.henallux.firstSpringProject.controller;
import com.spring.henallux.firstSpringProject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;


@Controller
public class LoginController {

    private final MessageSource messageSource;

    @Autowired
    public LoginController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/login")
    public String login(Model model,Locale locale) {
        model.addAttribute("user", new User());
        String translatedTitle = messageSource.getMessage("Login.namepage", null, locale);
        model.addAttribute("pageTitle", translatedTitle);
        return "integrated:login";
    }
}

