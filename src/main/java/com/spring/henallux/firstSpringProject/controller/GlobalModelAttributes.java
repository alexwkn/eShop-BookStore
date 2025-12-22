package com.spring.henallux.firstSpringProject.controller;

import com.spring.henallux.firstSpringProject.dataAccess.dao.CategoryTranslationDataAccess;
import com.spring.henallux.firstSpringProject.model.CartItem;
import com.spring.henallux.firstSpringProject.model.CategoryLabel;
import com.spring.henallux.firstSpringProject.service.CartService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@ControllerAdvice
public class GlobalModelAttributes {

    private final CategoryTranslationDataAccess categoryTx;
    private final CartService cartService;

    public GlobalModelAttributes(CategoryTranslationDataAccess categoryTx, CartService cartService) {
        this.categoryTx = categoryTx;
        this.cartService = cartService;
    }

    @ModelAttribute("categoriesNames")
    public List<CategoryLabel> categoriesNames() {
        return categoryTx.listCategoriesWithNames();
    }

    @ModelAttribute("cartCount")
    public int cartCount() {
        return cartService.getCount();
    }

    @ModelAttribute("cartItems")
    public List<CartItem> cartItems() {
        return cartService.getItems().entrySet().stream()
                .map(e -> new CartItem(e.getKey(), e.getValue()))
                .toList();
    }

    @ModelAttribute("cartTotal")
    public BigDecimal cartTotal() {
        return cartService.getTotal();
    }

}
