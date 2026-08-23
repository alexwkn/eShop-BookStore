package com.spring.henallux.firstSpringProject.dataAccess.repository;

import com.spring.henallux.firstSpringProject.dataAccess.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    List<OrderEntity> findByUserIdOrderByOrderIdDesc(Integer userId);

    Optional<OrderEntity> findByOrderIdAndUserId(
            Integer orderId,
            Integer userId
    );

    Optional<OrderEntity> findFirstByUserIdAndPaidFalseOrderByOrderIdDesc(Integer userId);
}
