package com.spring.henallux.firstSpringProject.controller;

import com.spring.henallux.firstSpringProject.dataAccess.dao.ProductDataAccess;
import com.spring.henallux.firstSpringProject.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductDataAccess products;

    @Autowired
    public CartController(CartService cartService, ProductDataAccess products) {
        this.cartService = cartService;
        this.products = products;
    }

    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable Integer id, @RequestParam(name = "quantity", defaultValue = "1") Integer quantity, HttpServletRequest request) {

        int qty = (quantity == null || quantity < 1) ? 1 : quantity;

        products.getAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .ifPresent(p -> cartService.addProduct(p, qty));

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/products");
    }

    @PostMapping("/update/{id}")
    public String updateQuantity(@PathVariable Integer id, @RequestParam("quantity") Integer quantity, HttpServletRequest request) {
        products.getAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .ifPresent(p -> cartService.updateQuantity(p, quantity));

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/products");
    }

    @PostMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Integer id, HttpServletRequest request) {
        products.getAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .ifPresent(cartService::removeProduct);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/products");
    }
}