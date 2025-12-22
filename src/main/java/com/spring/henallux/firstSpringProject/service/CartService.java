package com.spring.henallux.firstSpringProject.service;

import com.spring.henallux.firstSpringProject.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@SessionScope
public class CartService {
    private final Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        if (quantity < 1) {
            quantity = 1;
        }
        items.merge(product, quantity, Integer::sum);
    }

    public void removeProduct(Product product) {
        items.remove(product);
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public int getCount() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }

    public BigDecimal getTotal() {
        return items.entrySet().stream()
                .map(e -> e.getKey().getPrice().multiply(BigDecimal.valueOf(e.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void updateQuantity(Product product, int quantity) {
        if (quantity < 1) {
            items.remove(product);
        } else {
            items.put(product, quantity);
        }
    }

    public void clear() {
        items.clear();
    }
}
