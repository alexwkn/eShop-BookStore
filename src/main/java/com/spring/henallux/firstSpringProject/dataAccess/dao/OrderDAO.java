package com.spring.henallux.firstSpringProject.dataAccess.dao;

import com.spring.henallux.firstSpringProject.dataAccess.entity.OrderEntity;
import com.spring.henallux.firstSpringProject.model.Order;
import com.spring.henallux.firstSpringProject.dataAccess.repository.OrderRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderDAO implements OrderDataAccess {

    private final OrderRepository repo;

    public OrderDAO(OrderRepository repo) { this.repo = repo; }

    @Override
    @Transactional
    public Integer create(Integer userId) {
        OrderEntity e = new OrderEntity();
        e.setUserId(userId);
        e.setPaid(false);
        return repo.save(e).getOrderId();
    }

    @Override
    @Transactional
    public void markPaid(Integer orderId) {
        OrderEntity e = repo.findById(orderId).orElse(null);
        if (e != null) {
            e.setPaid(true);
            repo.save(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Order findById(Integer orderId) {
        return repo.findById(orderId).map(this::toModel).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findByUser(Integer userId) {
        return repo.findByUserIdOrderByOrderIdDesc(userId)
                .stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Integer orderId) {
        repo.deleteById(orderId); // cascade gérée par la DB pour Order_line
    }

    private Order toModel(OrderEntity e) {
        Order m = new Order();
        m.setOrderId(e.getOrderId());
        m.setUserId(e.getUserId());
        m.setPaid(e.isPaid());
        return m;
    }
}


