package com.spring.henallux.firstSpringProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping({ "/home"})
    public String home(Model model) {
        model.addAttribute("pageTitle", "BookStore Home");
        return "integrated:home";
    }
}
