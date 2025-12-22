package com.spring.henallux.firstSpringProject.dataAccess.util;

import com.spring.henallux.firstSpringProject.dataAccess.entity.DiscountEntity;
import com.spring.henallux.firstSpringProject.model.Discount;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;

@Component
public class DiscountConverter {

    private final Mapper mapper = new DozerBeanMapper();

    public DiscountEntity discountModelToEntity(Discount discount) {
        DiscountEntity entity = mapper.map(discount, DiscountEntity.class);
        entity.setCode(discount.getCode());
        entity.setDiscount(discount.getDiscount());
        entity.setValid(discount.isValid());
        return entity;
    }

    public Discount discountEntityToModel(DiscountEntity entity) {
        Discount discount = mapper.map(entity, Discount.class);
        discount.setCode(entity.getCode());
        discount.setDiscount(entity.getDiscount());
        discount.setValid(entity.isValid());
        return discount;
    }
}
