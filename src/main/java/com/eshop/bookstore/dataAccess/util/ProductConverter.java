package com.eshop.bookstore.dataAccess.util;

import com.eshop.bookstore.dataAccess.entity.ProductEntity;
import com.eshop.bookstore.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public Product toModel(ProductEntity e) {
        if (e == null) return null;
        return new Product(e.getId(), e.getName(), e.getPrice(), e.getDescription(), e.getCategoryId(),e.getAuthor());
    }
}
