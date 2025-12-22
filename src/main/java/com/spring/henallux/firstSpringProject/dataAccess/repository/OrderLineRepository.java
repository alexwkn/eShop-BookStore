package com.spring.henallux.firstSpringProject.dataAccess.repository;

import com.spring.henallux.firstSpringProject.dataAccess.entity.OrderLineEntity;
import com.spring.henallux.firstSpringProject.dataAccess.entity.OrderLineId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLineEntity, OrderLineId> {
    List<OrderLineEntity> findByIdOrderIdOrderByIdProductIdAsc(Integer orderId);
    void deleteByIdOrderIdAndIdProductId(Integer orderId, Integer productId);
}
