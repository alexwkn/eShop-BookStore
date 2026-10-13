package com.eshop.bookstore.dataAccess.repository;

import com.eshop.bookstore.dataAccess.entity.OrderEntity;
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
