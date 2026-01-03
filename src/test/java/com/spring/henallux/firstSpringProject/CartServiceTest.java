package com.spring.henallux.firstSpringProject;

import com.spring.henallux.firstSpringProject.model.Product;
import com.spring.henallux.firstSpringProject.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {
    private CartService cartService;
    private Product product;

    @BeforeEach
    void setUp() {
        cartService = new CartService();
        product = new Product(999, "test product", new BigDecimal("1000.00"), "",1, "Test Author");
    }

    @Test
    void updateQuantityInCart() {
        cartService.addProduct(product, 1);
        assertEquals(1, cartService.getItems().get(product));

        cartService.updateQuantity(product, 5);
        assertEquals(5, cartService.getItems().get(product));

        assertEquals(new BigDecimal("5000.00"), cartService.getTotal());
    }

    @Test
    void removeWhenQuantityIsZero() {
        cartService.addProduct(product, 2);
        assertTrue(cartService.getItems().containsKey(product));

        // if quantity < 1 -> remove
        cartService.updateQuantity(product, 0);
        assertFalse(cartService.getItems().containsKey(product));
    }

    @Test
    void addNegativeQuantity() {
        cartService.addProduct(product, 0);
        assertEquals(1, cartService.getItems().get(product));
    }
}