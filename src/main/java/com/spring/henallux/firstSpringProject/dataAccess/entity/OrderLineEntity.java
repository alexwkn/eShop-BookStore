package com.spring.henallux.firstSpringProject.dataAccess.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Order_line")
public class OrderLineEntity {

    @EmbeddedId
    private OrderLineId id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // prix unitaire copié lors de l’ajout

    public OrderLineId getId() { return id; }
    public void setId(OrderLineId id) { this.id = id; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
