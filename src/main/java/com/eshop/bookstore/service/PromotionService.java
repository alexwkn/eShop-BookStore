package com.eshop.bookstore.service;

import com.eshop.bookstore.model.Discount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

// calcule le montant des réductions (codes promo) sur un sous-total.
@Service
public class PromotionService {

    public BigDecimal computeDiscountAmount(BigDecimal subtotal, Discount discount) {
        if (subtotal == null) return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        if (discount == null || discount.getDiscount() == null || !Boolean.TRUE.equals(discount.isValid())) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return subtotal.multiply(discount.getDiscount())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal applyDiscount(BigDecimal subtotal, Discount discount) {
        BigDecimal scaledSubtotal = subtotal.setScale(2, RoundingMode.HALF_UP);
        BigDecimal discountAmount = computeDiscountAmount(scaledSubtotal, discount);
        return scaledSubtotal.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }
}
