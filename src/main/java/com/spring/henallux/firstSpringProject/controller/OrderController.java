package com.spring.henallux.firstSpringProject.controller;

import com.spring.henallux.firstSpringProject.model.Product;
import com.spring.henallux.firstSpringProject.model.User;
import com.spring.henallux.firstSpringProject.service.CartService;
import com.spring.henallux.firstSpringProject.dataAccess.entity.OrderEntity;
import com.spring.henallux.firstSpringProject.dataAccess.entity.OrderLineEntity;
import com.spring.henallux.firstSpringProject.dataAccess.entity.OrderLineId;
import com.spring.henallux.firstSpringProject.dataAccess.repository.OrderRepository;
import com.spring.henallux.firstSpringProject.dataAccess.repository.OrderLineRepository;
import com.spring.henallux.firstSpringProject.dataAccess.repository.ProductRepository;
import com.spring.henallux.firstSpringProject.dataAccess.entity.ProductEntity;
import com.spring.henallux.firstSpringProject.dataAccess.repository.UserRepository;
import com.spring.henallux.firstSpringProject.dataAccess.entity.UserEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final CartService cart;
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderController(CartService cart,
                           OrderRepository orderRepository,
                           OrderLineRepository orderLineRepository,
                           ProductRepository productRepository,
                           UserRepository userRepository) {
        this.cart = cart;
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/checkout")
    @Transactional
    public String checkout(@AuthenticationPrincipal com.spring.henallux.firstSpringProject.model.User principal) {
        String email = principal.getEmail();
        UserEntity user = userRepository.findByEmail(email);
        OrderEntity order = new OrderEntity();
        order.setUserId(user.getId());
        order.setPaid(false);
        order = orderRepository.save(order);

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product p = entry.getKey();
            Integer qty = entry.getValue();

            ProductEntity pEntity = productRepository.findById(p.getId())
                    .orElseThrow(() -> new IllegalStateException("Product not found: id=" + p.getId()));

            OrderLineEntity line = new OrderLineEntity();
            line.setId(new OrderLineId(order.getOrderId(), pEntity.getId()));
            line.setQuantity(qty);
            line.setPrice(pEntity.getPrice());

            orderLineRepository.save(line);
        }



        return "redirect:/payement/"+ order.getOrderId();
    }

}

