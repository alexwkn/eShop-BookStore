package com.eshop.bookstore.dataAccess.util;

import com.eshop.bookstore.dataAccess.entity.DiscountEntity;
import com.eshop.bookstore.model.Discount;
import org.springframework.stereotype.Component;

@Component
public class DiscountConverter {

    public DiscountEntity discountModelToEntity(Discount discount) {
        if (discount == null) return null;
        return new DiscountEntity(discount.getCode(), discount.getDiscount(), discount.isValid());
    }

    public Discount discountEntityToModel(DiscountEntity entity) {
        if (entity == null) return null;
        return new Discount(entity.getCode(), entity.getDiscount(), entity.isValid());
    }
}
