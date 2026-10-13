package com.eshop.bookstore.dataAccess.dao;



import com.eshop.bookstore.model.Order;
import java.util.List;

public interface OrderDataAccess {
    Integer create(Integer userId);
    void markPaid(Integer orderId);
    Order findById(Integer orderId);
    List<Order> findByUser(Integer userId);
    void delete(Integer orderId);
}
