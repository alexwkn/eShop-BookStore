package com.spring.henallux.firstSpringProject.controller;

import com.spring.henallux.firstSpringProject.dataAccess.dao.DiscountDataAccess;
import com.spring.henallux.firstSpringProject.model.Discount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final DiscountDataAccess discountDao;

    public AdminController(DiscountDataAccess discountDao) {
        this.discountDao = discountDao;
    }

    @GetMapping
    public String adminRoot() {
        return "redirect:/admin/promo-codes";
    }

    @GetMapping("/promo-codes")
    public String promoCodesPage(Model model) {
        if (!model.containsAttribute("discount")) {
            model.addAttribute("discount", new Discount());
        }
        model.addAttribute("promoCodes", discountDao.findAll());
        return "integrated:admin";
    }
    @PostMapping("/promo-codes")
    public String createPromoCode(@Valid @ModelAttribute("discount") Discount form,
                                  BindingResult br,
                                  Model model,
                                  RedirectAttributes ra) {

        if (br.hasErrors()) {
            model.addAttribute("promoCodes", discountDao.findAll());
            return "integrated:admin";
        }
        if (discountDao.existsByCode(form.getCode())) {
            br.rejectValue("code", "admin.error.code.exists", "Ce code existe déjà.");
            model.addAttribute("promoCodes", discountDao.findAll());
            return "integrated:admin";
        }
        try {
            discountDao.save(form);
            ra.addFlashAttribute("successCode", "admin.success.created");
            ra.addFlashAttribute("successArgs", new Object[]{form.getCode()});
            return "redirect:/admin/promo-codes";
        } catch (Exception e) {
            br.reject("admin.error.technical", "Erreur technique.");
            model.addAttribute("promoCodes", discountDao.findAll());
            return "integrated:admin";
        }
    }
    @PostMapping("/promo-codes/{code}/delete")
    public String deletePromoCode(@PathVariable("code") String code,
                                  RedirectAttributes ra) {
        if (!discountDao.existsByCode(code)) {
            ra.addFlashAttribute("errorCode", "admin.error.code.notfound");
            ra.addFlashAttribute("errorArgs", new Object[]{code});
            return "redirect:/admin/promo-codes";
        }
        try {
            discountDao.deleteByCode(code);
            ra.addFlashAttribute("successCode", "admin.success.deleted");
            ra.addFlashAttribute("successArgs", new Object[]{code});
        } catch (Exception e) {
            ra.addFlashAttribute("errorCode", "admin.error.technical");
        }
        return "redirect:/admin/promo-codes";
    }
}
