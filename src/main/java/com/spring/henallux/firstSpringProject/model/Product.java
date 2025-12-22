package com.spring.henallux.firstSpringProject.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final Integer id;
    private final String name;
    private final BigDecimal price;
    private final String description;
    private final Integer categoryId;
    private final String author;

    public Product(Integer id, String name, BigDecimal price, String description, Integer categoryId,String author) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
        this.author = author;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getDescription() { return description; }
    public Integer getCategoryId() { return categoryId; }
    public String getAuthor() { return author; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
