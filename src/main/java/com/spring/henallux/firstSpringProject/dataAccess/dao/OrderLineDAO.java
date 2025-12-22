package com.spring.henallux.firstSpringProject.dataAccess.dao;


import com.spring.henallux.firstSpringProject.dataAccess.entity.OrderLineEntity;
import com.spring.henallux.firstSpringProject.dataAccess.entity.OrderLineId;
import com.spring.henallux.firstSpringProject.model.OrderLine;
import com.spring.henallux.firstSpringProject.dataAccess.repository.OrderLineRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderLineDAO implements OrderLineDataAccess {

    private final OrderLineRepository repo;

    public OrderLineDAO(OrderLineRepository repo) { this.repo = repo; }

    @Override
    @Transactional
    public void upsertLine(Integer orderId, Integer productId, Integer quantity, BigDecimal unitPrice) {
        OrderLineId id = new OrderLineId(orderId, productId);
        OrderLineEntity e = repo.findById(id).orElseGet(() -> {
            OrderLineEntity ne = new OrderLineEntity();
            ne.setId(id);
            return ne;
        });
        e.setQuantity(quantity);
        e.setPrice(unitPrice);
        repo.save(e);
    }

    @Override
    @Transactional
    public void removeLine(Integer orderId, Integer productId) {
        repo.deleteByIdOrderIdAndIdProductId(orderId, productId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderLine> findByOrder(Integer orderId) {
        return repo.findByIdOrderIdOrderByIdProductIdAsc(orderId)
                .stream().map(this::toModel).collect(Collectors.toList());
    }
    private OrderLine toModel(OrderLineEntity e) {
        OrderLine m = new OrderLine();
        m.setOrderId(e.getId().getOrderId());
        m.setProductId(e.getId().getProductId());
        m.setQuantity(e.getQuantity());
        m.setPrice(e.getPrice());
        return m;
    }

}
