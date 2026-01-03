package com.spring.henallux.firstSpringProject;

import com.spring.henallux.firstSpringProject.dataAccess.dao.OrderDAO;
import com.spring.henallux.firstSpringProject.dataAccess.entity.OrderEntity;
import com.spring.henallux.firstSpringProject.dataAccess.repository.OrderRepository;
import com.spring.henallux.firstSpringProject.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderDAOTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderDAO orderDAO;

    private OrderEntity orderEntity;

    @BeforeEach
    void setUp() {
        orderEntity = new OrderEntity();
        orderEntity.setOrderId(1);
        orderEntity.setUserId(42);
        orderEntity.setPaid(false);
    }

    @Test
    void createOrder() {
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        Integer orderId = orderDAO.create(42);

        assertThat(orderId).isEqualTo(1);
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    void markPaid() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(orderEntity));
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);

        orderDAO.markPaid(1);

        assertThat(orderEntity.isPaid()).isTrue();
        verify(orderRepository).save(orderEntity);
    }

    @Test
    void assertOrderById() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(orderEntity));

        Order result = orderDAO.findById(1);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(42);
        assertThat(result.isPaid()).isFalse();
    }

    @Test
    void findNonexistentOrder() {
        when(orderRepository.findById(999)).thenReturn(Optional.empty());

        Order result = orderDAO.findById(999);

        assertThat(result).isNull();
    }

    @Test
    void findUserOrder() {
        OrderEntity e1 = new OrderEntity();
        e1.setOrderId(1); e1.setUserId(42); e1.setPaid(false);
        OrderEntity e2 = new OrderEntity();
        e2.setOrderId(2); e2.setUserId(42); e2.setPaid(true);

        when(orderRepository.findByUserIdOrderByOrderIdDesc(42))
                .thenReturn(Arrays.asList(e1, e2));

        List<Order> results = orderDAO.findByUser(42);

        assertThat(results).hasSize(2);
        assertThat(results.get(0).getOrderId()).isEqualTo(1);
        assertThat(results.get(1).isPaid()).isTrue();
    }

    @Test
    void deleteOrderById() {
        orderDAO.delete(1);

        verify(orderRepository, times(1)).deleteById(1);
    }
}