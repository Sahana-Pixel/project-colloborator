package com.example.springboot_check;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for testing with frontend JS
            .authorizeHttpRequests(auth -> auth
                // Public routes: Landing page and static resources
                .requestMatchers(
                    "/",                    // Landing page root
                    "/index.html",          // Landing page
                    "/css/**",              // CSS static resources
                    "/js/**"                // JavaScript static resources
                ).permitAll()
                // OAuth2 routes: Must be public for authentication flow to work
                .requestMatchers(
                    "/oauth2/**",           // OAuth2 authorization endpoints (e.g., /oauth2/authorization/github)
                    "/login/oauth2/**",     // OAuth2 callback endpoints (e.g., /login/oauth2/code/github)
                    "/logout"               // Logout endpoint (public to allow logout without auth)
                ).permitAll()
                // SECURITY FIX: All /projects/** endpoints now require authentication
                // Previously these were public, allowing unauthorized access to project operations
                .requestMatchers("/projects/**").authenticated()
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/index.html")   // Custom login page (landing page)
                .defaultSuccessUrl("/dashboard", true) // After successful login, redirect to dashboard
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/index.html")  // After logout, redirect to landing page
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();
    }
}
