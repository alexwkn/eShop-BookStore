package com.eshop.bookstore.controller;

import com.eshop.bookstore.model.User;
import com.eshop.bookstore.model.UserUpdate;
import com.eshop.bookstore.service.InputSanitizerService;
import com.eshop.bookstore.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/authenticated")
public class AuthenticatedController {

    private final UserService userService;
    private final InputSanitizerService inputSanitizerService;

    public AuthenticatedController(UserService userService, InputSanitizerService inputSanitizerService) {
        this.userService = userService;
        this.inputSanitizerService = inputSanitizerService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String authenticated(Model model, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        UserUpdate form = new UserUpdate();
        form.setUsername(userDetails.getUsername());
        form.setAddress(userDetails.getAddress());
        form.setPhoneNumber(userDetails.getPhoneNumber());
        model.addAttribute("userUpdate", form);
        return "integrated:authenticated";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String modifyUser(@Valid @ModelAttribute("userUpdate") UserUpdate form,
                             BindingResult br,
                             Authentication authentication,
                             HttpServletRequest request) {
        String originalUsername = form.getUsername();

        if (originalUsername != null) {

            String sanitizedUsername = inputSanitizerService.sanitizePlainText(originalUsername);

            if (!originalUsername.equals(sanitizedUsername)) {

                br.rejectValue(
                        "username",
                        "security.username.unsafe",
                        "Le nom d'utilisateur contient du contenu interdit"
                );
            }
        }



        if (form.getPassword() != null && !form.getPassword().isBlank()) {
            if (form.getConfirmPassword() == null || !form.getPassword().equals(form.getConfirmPassword())) {
                br.rejectValue("confirmPassword", "password.mismatch", "Les mots de passe ne correspondent pas");
            }
        }
        if (br.hasErrors()) {
            return "integrated:authenticated";
        }
        String email = ((User) authentication.getPrincipal()).getEmail();
        User updatedUser = userService.updateUser(email, form);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUser,
                updatedUser.getPassword(),
                updatedUser.getAuthorities());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(newAuth);
        request.getSession().setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );
        return "redirect:/home";
    }

}
