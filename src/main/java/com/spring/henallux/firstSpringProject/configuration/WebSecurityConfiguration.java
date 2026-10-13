package com.spring.henallux.firstSpringProject.configuration;

import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    private static final String LOGIN_REQUEST = "/login";
    private static final String[] AUTHORIZED_REQUESTS_ANYBODY = new String[]{
            "/", "/home", "/css/**", "/images/**", "/login", "/register/**", "/template/**", "/products/**", "/categories/**", "/info/**", "/cart/**"
    };
    private static final String[] AUTHORIZED_REQUESTS_ADMIN = new String[]{"/admin/**"};

    private static final String CONTENT_SECURITY_POLICY =
            "default-src 'self'; " +
                    "script-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net; " +
                    "style-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net; " +
                    "font-src 'self' data: https://cdn.jsdelivr.net; " +
                    "img-src 'self' data: https://placehold.jp; " +
                    "connect-src 'self'; " +
                    "form-action 'self' https://www.sandbox.paypal.com; " +
                    "frame-ancestors 'none'; " +
                    "base-uri 'self'; " +
                    "object-src 'none'";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(Customizer.withDefaults()); // Default CSRF protection

        http
                .authorizeRequests()// We define the authorization here
                // Accessible only to users with ROLE_ADMIN
                .antMatchers(AUTHORIZED_REQUESTS_ADMIN).hasRole("ADMIN")
                // Open to everyone
                .antMatchers(AUTHORIZED_REQUESTS_ANYBODY).permitAll()
                // All other pages require authentication
                .anyRequest().authenticated()

                .and()

                .formLogin()
                .usernameParameter("email")
                .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                .loginPage(LOGIN_REQUEST)
                .permitAll()

                .and()

                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID") // Remove the session cookie from the client
                .permitAll();

        http.headers(headers -> headers
                // Reduce the potential for XSS Injection
                .contentSecurityPolicy(csp -> csp.policyDirectives(CONTENT_SECURITY_POLICY))
                // Avoid reveiling the full URL to external sites
                .addHeaderWriter(
                        new StaticHeadersWriter(
                                "Referrer-Policy",
                                "strict-origin-when-cross-origin"
                        )
                )
                // Clickjacking protection
                .frameOptions()
                .deny()
        );


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return factory -> factory.addContextCustomizers(context -> {
            Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
            cookieProcessor.setSameSiteCookies("Lax"); // Lax / None / Strict
            context.setCookieProcessor(cookieProcessor);
        });
    }
}