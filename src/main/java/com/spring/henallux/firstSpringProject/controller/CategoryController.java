package com.spring.henallux.firstSpringProject.controller;

import com.spring.henallux.firstSpringProject.dataAccess.dao.CategoryTranslationDataAccess;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {

    private final CategoryTranslationDataAccess i18n;

    public CategoryController(CategoryTranslationDataAccess i18n) {
        this.i18n = i18n;
    }

    @GetMapping("/categories")
    public String list(Model model) {
        model.addAttribute("categories", i18n.listCategoriesWithNames());
        return "integrated:categories";
    }
}
