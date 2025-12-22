package com.spring.henallux.firstSpringProject.dataAccess.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable

public class OrderLineId implements Serializable {
    @Column(name = "order_id") // Cannot resolve column 'order_id'
    private Integer orderId;

    @Column(name = "product_id") // Cannot resolve column 'product_id'
    private Integer productId;

    public OrderLineId() {}
    public OrderLineId(Integer orderId, Integer productId) {
        this.orderId = orderId; this.productId = productId;
    }



    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }







    // ca fait quoi ca c pour quoi faire ?
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderLineId)) return false;
        OrderLineId that = (OrderLineId) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(productId, that.productId);
    }
    @Override public int hashCode() { return Objects.hash(orderId, productId); }
}
