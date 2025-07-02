package com.example.TenantEase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class TenantEaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(TenantEaseApplication.class, args);
	}

}
