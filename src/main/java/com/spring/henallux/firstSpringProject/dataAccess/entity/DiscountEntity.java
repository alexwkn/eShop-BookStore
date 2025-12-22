package com.spring.henallux.firstSpringProject.dataAccess.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "discount")
public class DiscountEntity {

    @Id
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @Column(name = "discount", precision = 5, scale = 2, nullable = false)
    private BigDecimal discount;

    @Column(name = "is_valid", nullable = false)
    private boolean valid = true;

    public DiscountEntity() {}

    public DiscountEntity(String code, BigDecimal discount, boolean valid) {
        this.code = code;
        this.discount = discount;
        this.valid = valid;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }

    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }
}
