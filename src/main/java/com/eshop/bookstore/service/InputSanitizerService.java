package com.eshop.bookstore.service;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.stereotype.Service;

@Service
public class InputSanitizerService {
    private final PolicyFactory plainTextPolicy = new HtmlPolicyBuilder().toFactory();

    public String sanitizePlainText(String input) {
        if (input == null) {
            return null;
        }
        return plainTextPolicy.sanitize(input);
    }

    public boolean isSafePlainText(String input) {
        if (input == null) {
            return true;
        }

        return input.equals(sanitizePlainText(input));
    }
}
