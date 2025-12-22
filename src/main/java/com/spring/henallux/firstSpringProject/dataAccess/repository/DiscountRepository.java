package com.spring.henallux.firstSpringProject.dataAccess.repository;

import com.spring.henallux.firstSpringProject.dataAccess.entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<DiscountEntity, String> {
    boolean existsByCode(String code);
    void deleteByCode(String code);
    DiscountEntity findByCode(String code);
}
