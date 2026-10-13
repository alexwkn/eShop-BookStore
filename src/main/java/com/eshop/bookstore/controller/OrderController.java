package com.eshop.bookstore.controller;

import com.eshop.bookstore.model.Product;
import com.eshop.bookstore.model.User;
import com.eshop.bookstore.service.CartService;
import com.eshop.bookstore.dataAccess.entity.OrderEntity;
import com.eshop.bookstore.dataAccess.entity.OrderLineEntity;
import com.eshop.bookstore.dataAccess.entity.OrderLineId;
import com.eshop.bookstore.dataAccess.repository.OrderRepository;
import com.eshop.bookstore.dataAccess.repository.OrderLineRepository;
import com.eshop.bookstore.dataAccess.repository.ProductRepository;
import com.eshop.bookstore.dataAccess.entity.ProductEntity;
import com.eshop.bookstore.dataAccess.repository.UserRepository;
import com.eshop.bookstore.dataAccess.entity.UserEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    public String checkout(@AuthenticationPrincipal com.eshop.bookstore.model.User principal) {
        String email = principal.getEmail();
        UserEntity user = userRepository.findByEmail(email);

        OrderEntity order = orderRepository
                .findFirstByUserIdAndPaidFalseOrderByOrderIdDesc(user.getId())
                .orElseGet(() -> {
                    OrderEntity newOrder = new OrderEntity();
                    newOrder.setUserId(user.getId());
                    newOrder.setPaid(false);
                    return orderRepository.save(newOrder);
                });

        Set<Integer> cartProductIds = new HashSet<>();

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product p = entry.getKey();
            Integer qty = entry.getValue();

            ProductEntity pEntity = productRepository.findById(p.getId())
                    .orElseThrow(() -> new IllegalStateException("Product not found: id=" + p.getId()));

            cartProductIds.add(pEntity.getId());

            OrderLineId lineId = new OrderLineId(order.getOrderId(), pEntity.getId());
            OrderLineEntity line = orderLineRepository.findById(lineId).orElseGet(OrderLineEntity::new);
            line.setId(lineId);
            line.setQuantity(qty);
            line.setPrice(pEntity.getPrice());

            orderLineRepository.save(line);
        }

        for (OrderLineEntity existingLine : orderLineRepository.findByIdOrderIdOrderByIdProductIdAsc(order.getOrderId())) {
            if (!cartProductIds.contains(existingLine.getId().getProductId())) {
                orderLineRepository.deleteByIdOrderIdAndIdProductId(order.getOrderId(), existingLine.getId().getProductId());
            }
        }

        // Prix réel de la commande, calculé à partir des lignes enregistrées (pas du panier en session)
        BigDecimal totalPrice = orderLineRepository.findByIdOrderIdOrderByIdProductIdAsc(order.getOrderId()).stream()
                .map(line -> line.getPrice().multiply(BigDecimal.valueOf(line.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        return "redirect:/payement/"+ order.getOrderId();
    }

}

