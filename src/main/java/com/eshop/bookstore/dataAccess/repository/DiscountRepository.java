package com.eshop.bookstore.dataAccess.repository;

import com.eshop.bookstore.dataAccess.entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<DiscountEntity, String> {
    boolean existsByCode(String code);
    void deleteByCode(String code);
    DiscountEntity findByCode(String code);
}
