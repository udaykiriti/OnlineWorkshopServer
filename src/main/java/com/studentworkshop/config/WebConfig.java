package com.studentworkshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    // Define a Bean that customizes the WebMvcConfigurer for CORS configuration
    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            // Override the addCorsMappings method to configure CORS settings
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Allow CORS requests to any endpoint that matches /api/**
                registry.addMapping("/api/**")
                        // Specify the allowed origins for cross-origin requests
                        .allowedOrigins("http://localhost:3000", "https://online-workshop-nine.vercel.app")
                        // Define the HTTP methods that are allowed for CORS requests
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        // Allow all headers in the request
                        .allowedHeaders("*");
            }
        };
    }
}
