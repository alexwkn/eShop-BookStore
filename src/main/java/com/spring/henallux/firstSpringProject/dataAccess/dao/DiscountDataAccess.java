package com.spring.henallux.firstSpringProject.dataAccess.dao;

import com.spring.henallux.firstSpringProject.model.Discount;

import java.util.List;

public interface DiscountDataAccess {
    List<Discount> findAll();
    Discount findByCode(String code);
    Discount save(Discount discount);
    void deleteByCode(String code);
    boolean existsByCode(String code);
}
