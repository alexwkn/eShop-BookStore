package com.eshop.bookstore.dataAccess.dao;

import com.eshop.bookstore.model.Discount;

import java.util.List;

public interface DiscountDataAccess {
    List<Discount> findAll();
    Discount findByCode(String code);
    Discount save(Discount discount);
    void deleteByCode(String code);
    boolean existsByCode(String code);
}
