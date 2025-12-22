package com.spring.henallux.firstSpringProject.dataAccess.util;

import com.spring.henallux.firstSpringProject.dataAccess.entity.ProductEntity;
import com.spring.henallux.firstSpringProject.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public Product toModel(ProductEntity e) {
        if (e == null) return null;
        return new Product(e.getId(), e.getName(), e.getPrice(), e.getDescription(), e.getCategoryId(),e.getAuthor());
    }
}
