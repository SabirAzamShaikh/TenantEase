package com.example.TenantEase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class TenantEaseApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(TenantEaseApplication.class, args);
    }
}
