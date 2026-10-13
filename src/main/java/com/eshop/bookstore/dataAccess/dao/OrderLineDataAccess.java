package com.eshop.bookstore.dataAccess.dao;



import com.eshop.bookstore.model.OrderLine;
import java.math.BigDecimal;
import java.util.List;

public interface OrderLineDataAccess {
    void upsertLine(Integer orderId, Integer productId, Integer quantity, BigDecimal unitPrice);
    void removeLine(Integer orderId, Integer productId);
    List<OrderLine> findByOrder(Integer orderId);
}

