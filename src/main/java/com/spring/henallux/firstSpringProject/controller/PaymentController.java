package com.spring.henallux.firstSpringProject.controller;

import com.spring.henallux.firstSpringProject.dataAccess.dao.DiscountDataAccess;
import com.spring.henallux.firstSpringProject.dataAccess.entity.UserEntity;
import com.spring.henallux.firstSpringProject.dataAccess.repository.OrderLineRepository;
import com.spring.henallux.firstSpringProject.dataAccess.repository.OrderRepository;
import com.spring.henallux.firstSpringProject.dataAccess.repository.UserRepository;
import com.spring.henallux.firstSpringProject.model.Discount;
import com.spring.henallux.firstSpringProject.model.User;
import com.spring.henallux.firstSpringProject.service.CartService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.spring.henallux.firstSpringProject.dataAccess.entity.OrderEntity;


import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

@Controller
@RequestMapping("/payement")
public class PaymentController {

    private final CartService cartService;
    private final DiscountDataAccess discountDao;
    private final MessageSource messageSource;


    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final UserRepository userRepository;

    private static final String PAYPAL_URL = "https://www.sandbox.paypal.com/cgi-bin/webscr";
    private static final String BUSINESS = "sb-qmx1j48304475@business.example.com";
    private static final String CLIENT_ID = "Af39HyotBOs9cX7U8kRDy9MW5JoyeBZquz4fzzjzVD5VAOyfCZCXRuG-pRHIkQHK4lbjlDYvtqYWcard";
    private static final String RETURN_URL = "https://localhost:8082/bookstore/payement/success";
    private static final String CANCEL_URL = "https://localhost:8082/bookstore/payement/failed";
    private static final String CURRENCY = "EUR";

    private static final String SESSION_COUPON = "appliedCoupon";

    public PaymentController(CartService cartService, DiscountDataAccess discountDao, MessageSource messageSource, OrderRepository orderRepository, OrderLineRepository orderLineRepository, UserRepository userRepository) {
        this.cartService = cartService;
        this.discountDao = discountDao;
        this.messageSource = messageSource;
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.userRepository = userRepository;
    }

    // Recalcule le prix réel à partir des lignes de commande enregistrées (pas du panier en session),
    // en réappliquant le code promo actif pour cette session au moment du paiement.
    private BigDecimal computeRealTotal(Integer orderId, HttpSession session) {
        BigDecimal subtotal = orderLineRepository.findByIdOrderIdOrderByIdProductIdAsc(orderId).stream()
                .map(line -> line.getPrice().multiply(BigDecimal.valueOf(line.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        String appliedCode = (String) session.getAttribute(SESSION_COUPON);
        if (appliedCode == null) {
            return subtotal;
        }

        Discount d = discountDao.findByCode(appliedCode);
        if (d == null || !Boolean.TRUE.equals(d.isValid())) {
            return subtotal;
        }

        BigDecimal discountAmount = subtotal.multiply(d.getDiscount())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return subtotal.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }

    private OrderEntity getOwnedOrder(Integer orderId, User principal) {

        if (principal == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Utilisateur non authentifié"
            );
        }

        UserEntity currentUser =
                userRepository.findByEmail(principal.getEmail());

        if (currentUser == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Utilisateur introuvable"
            );
        }

        return orderRepository
                .findByOrderIdAndUserId(
                        orderId,
                        currentUser.getId()
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Commande introuvable"
                        )
                );
    }

    @GetMapping("/{orderId}")
    public String paymentForm(Model model, @PathVariable("orderId") Integer orderId, HttpSession session, @AuthenticationPrincipal User principal) {
        getOwnedOrder(orderId, principal);

        model.addAttribute("paypalUrl", PAYPAL_URL);
        model.addAttribute("business", BUSINESS);
        model.addAttribute("clientId", CLIENT_ID);
        model.addAttribute("returnUrl", RETURN_URL + "/" + orderId);
        model.addAttribute("cancelUrl", CANCEL_URL);
        model.addAttribute("currency", CURRENCY);
        model.addAttribute("orderId", orderId);

        // total toujours à 2 décimales
        BigDecimal total = cartService.getTotal().setScale(2, RoundingMode.HALF_UP);
        model.addAttribute("paymentCartItems", cartService.getItems());

        String appliedCode = (String) session.getAttribute(SESSION_COUPON);
        BigDecimal discountRate = BigDecimal.ZERO;
        BigDecimal discountAmount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal finalTotal = total;

        if (appliedCode != null) {
            Discount d = discountDao.findByCode(appliedCode);

            if (d != null && Boolean.TRUE.equals(d.isValid())) {
                discountRate = d.getDiscount();

                discountAmount = total.multiply(discountRate)
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                        .setScale(2, RoundingMode.HALF_UP);

                finalTotal = total.subtract(discountAmount)
                        .setScale(2, RoundingMode.HALF_UP);

                model.addAttribute("discountRate", discountRate);
                model.addAttribute("discountAmount", discountAmount);
            } else {
                session.removeAttribute(SESSION_COUPON);
                appliedCode = null;
            }
        }

        model.addAttribute("amount", finalTotal);
        model.addAttribute("subtotal", total);
        model.addAttribute("appliedCode", appliedCode);
        //cartService.clear();

        return "integrated:payement";
    }

    @GetMapping("/success/{orderId}")
    @Transactional
    public String paymentSuccess(@PathVariable("orderId") Integer orderId, HttpSession session, @AuthenticationPrincipal User principal) {

        OrderEntity order = getOwnedOrder(orderId, principal);
        order.setPaid(true);
        order.setTotalPrice(computeRealTotal(orderId, session));
        orderRepository.save(order);

        session.removeAttribute(SESSION_COUPON);
        cartService.clear();
        return "integrated:payment-success";
    }

    @GetMapping("/failed")
    public String paymentFailed() {
        return "integrated:payment-failed";
    }

    @PostMapping
    public String applyCoupon(@RequestParam(value = "code", required = false) String rawCode,
                              @RequestParam("orderId") Integer orderId,
                              HttpSession session,
                              RedirectAttributes ra,
                              Locale locale,  @AuthenticationPrincipal User principal) {
        String code = (rawCode == null) ? "" : rawCode.trim();

        getOwnedOrder(orderId, principal);

        if (code.isEmpty()) {
            ra.addFlashAttribute("error", messageSource.getMessage("coupon.error.empty", null, locale));
            return "redirect:/payement/" + orderId;
        }

        Discount d = discountDao.findByCode(code);
        boolean invalid =
                d == null
                        || !Boolean.TRUE.equals(d.isValid())
                        || d.getDiscount() == null
                        || d.getDiscount().compareTo(BigDecimal.ZERO) < 0
                        || d.getDiscount().compareTo(new BigDecimal("100")) > 0;

        if (invalid) {
            ra.addFlashAttribute("error", messageSource.getMessage("coupon.error.invalid", null, locale));
            session.removeAttribute(SESSION_COUPON);
            return "redirect:/payement/" + orderId;
        }

        session.setAttribute(SESSION_COUPON, code);
        ra.addFlashAttribute("success", messageSource.getMessage("coupon.success", null, locale));
        return "redirect:/payement/" + orderId;
    }
}