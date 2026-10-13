package com.eshop.bookstore.dataAccess.dao;

import com.eshop.bookstore.model.Product;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Optional;

public interface ProductDataAccess {
    List<Product> getAll();
    List<Product> getByCategory(Integer categoryId);
    Optional<Resource> getImage(Integer productId);
}
