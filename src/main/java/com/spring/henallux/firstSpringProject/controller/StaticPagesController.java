package com.spring.henallux.firstSpringProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/info")
public class StaticPagesController {

    @GetMapping("/about-us")
    public String aboutUs(Model model) {
        model.addAttribute("pageTitle", "About us");
        model.addAttribute("bgImage", "/images/logo.png");
        return "integrated:info/about-us";
    }

    @GetMapping("/privacy-policy")
    public String privacy(Model model) {
        model.addAttribute("pageTitle", "Privacy Policy");
        return "integrated:info/privacy-policy";
    }

    @GetMapping("/terms")
    public String terms(Model model) {
        model.addAttribute("pageTitle", "Terms & Conditions");
        return "integrated:info/terms";
    }

    @GetMapping("/shipping")
    public String shipping(Model model) {
        model.addAttribute("pageTitle", "Shipping");
        return "integrated:info/shipping";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("pageTitle", "Contact");
        return "integrated:info/contact";
    }
}

