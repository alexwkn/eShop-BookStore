package com.spring.henallux.firstSpringProject.model;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class Discount {

    @NotBlank
    @Size(max = 50)
    private String code;

    @NotNull
    @Digits(integer = 3, fraction = 2)
    @DecimalMin(value = "0.00")
    private BigDecimal discount;

    private boolean valid = true;

    public Discount() {}

    public Discount(String code, BigDecimal discount, boolean valid) {
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
