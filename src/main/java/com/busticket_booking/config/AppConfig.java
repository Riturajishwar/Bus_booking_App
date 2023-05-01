package com.busticket_booking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
public class AppConfig {

    @Value("${spring.servlet.multipart.location}")
    private String uploadLocation;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement(uploadLocation);
    }
}
