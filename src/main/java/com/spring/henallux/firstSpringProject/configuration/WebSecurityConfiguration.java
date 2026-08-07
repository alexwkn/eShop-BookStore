package com.spring.henallux.firstSpringProject.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_REQUEST = "/login";
    private static final String[] AUTHORIZED_REQUESTS_ANYBODY = new String[]{
            "/home", "/css/**", "/images/**", "/login", "/register/**", "/template/**", "/products/**", "/categories/**", "/info/**", "/cart/**"
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

    private UserDetailsService userDetailsServiceImpl;

    @Autowired
    public WebSecurityConfiguration(UserDetailsService userDetailsServiceImplementation) {
        this.userDetailsServiceImpl = userDetailsServiceImplementation;
    }
    /**
     * We do the configuration of spring security here.
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Content Security Policy : Reduce the potential for XSS Injection
        http.headers()
                .contentSecurityPolicy(contentSecurityPolicy ->
                        contentSecurityPolicy.policyDirectives(
                                CONTENT_SECURITY_POLICY
                        )
                );

        // Referrer Policy : Avoid revealing the full URL to external sites
        http.headers()
                .addHeaderWriter(
                        new StaticHeadersWriter(
                                "Referrer-Policy",
                                "strict-origin-when-cross-origin"
                        )
                );

        // Permissions Policy : Interdit caméra, microphone et géolocalisation
        http.headers()
                .addHeaderWriter(
                        new StaticHeadersWriter(
                                "Permissions-Policy",
                                "geolocation=(), camera=(), microphone=()"
                        )
                );

        // Protection against clickjacking
        http.headers()
                .frameOptions()
                .deny();

        // Force the browser to continue using HTTPS
        http.headers()
                .httpStrictTransportSecurity()
                .includeSubDomains(false)
                .maxAgeInSeconds(31536000);

        http
                .authorizeRequests() // We define the authorization here
                .antMatchers(AUTHORIZED_REQUESTS_ADMIN).hasRole("ADMIN") // For the request to "/admin", the user needs to be an admin
                .antMatchers(AUTHORIZED_REQUESTS_ANYBODY).permitAll() // For the request to the index page, any user has access
                .anyRequest().authenticated() // For all the other requests, the user needs to be authenticated

                .and()

                .formLogin() // We define the login part here.
                .successHandler(new SavedRequestAwareAuthenticationSuccessHandler()) // provided by spring to redirect to the last request
                .loginPage(LOGIN_REQUEST) // We specify a login page. Otherwise spring creates one by default
                .permitAll() // To make the login page the available for any user
                .defaultSuccessUrl("/home") // URL to return if login is successfull

                .and()

                .logout() // We define the logout part here - By default : URL = "/logout"
                .logoutUrl("/logout") // Define the endpoint URL to trigger logout
                .logoutSuccessUrl("/home")  // URL to return if logout is successfull
                .invalidateHttpSession(true) // Invalidate the current HTTP session
                .clearAuthentication(true) // Clear the security context authentication
                .deleteCookies("JSESSIONID") // Remove the session cookie from the client
                .permitAll(); // To make the logout available for any user
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
    }
}