package com.example.springboot_check;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main entry point of the Spring Boot application.
 * Also serves as a REST controller for testing endpoints.
 */
@SpringBootApplication  // Enables Spring Boot auto-configuration
@RestController        // Allows defining HTTP endpoints
public class SpringbootCheckApplication {

    /**
     * Main method to launch the application.
     * Starts the embedded server (default port 8080).
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringbootCheckApplication.class, args);
    }

    /**
     * Simple GET endpoint for testing backend.
     * URL: /hello
     * @return Confirmation string that server is running
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hi, Sahana! Your backend is working 🎉";
    }
}
