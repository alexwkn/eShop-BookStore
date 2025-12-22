package com.spring.henallux.firstSpringProject.controller;

import com.spring.henallux.firstSpringProject.model.User;
import com.spring.henallux.firstSpringProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {

    private final UserService userService;

    private final MessageSource messageSource;

    @Autowired
    public RegisterController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @RequestMapping(method = RequestMethod.GET )
    public String home(Model model, Locale local) {
        model.addAttribute("user", new User());
        String translatedTitle = messageSource.getMessage("register.namepage", null, local);
        model.addAttribute("pageTitle", translatedTitle);
        return "integrated:register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("user") User user, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "integrated:register";
        }

        if (userService.emailExists(user.getEmail())) {
            errors.rejectValue("email", "email.exists", "Un compte existe déjà avec cet email.");
            return "integrated:register";
        }

        userService.registerNewUser(user);
        return "redirect:/home";
    }
}
