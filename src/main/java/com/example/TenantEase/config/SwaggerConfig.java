package com.example.TenantEase.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail("sabirazamshaikh01@gmail.com");
        contact.setName("Sabir shaikh");
        contact.setUrl("https://www.dummysite.com/");
        License Bealicense = new License().name("Tenant License").url("https://www.dummysite.com/");

        Info info = new Info().title("TENANTEASE Application").version("V.2.0").contact(contact).description(
                        "Application made For the Owner OF the House and Flats to manages Their Tenants")
                .termsOfService("https://www.dummysite.com/").license(Bealicense);

        var securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP).name("bearer").scheme("bearer")
                .bearerFormat("JWT").in(SecurityScheme.In.HEADER).name("Authorization");
        var securityComponent = new Components().addSecuritySchemes("bearer", securityScheme);
        var securityItem = new SecurityRequirement().addList("bearer");

        return new OpenAPI().info(info).components(securityComponent).addSecurityItem(securityItem);
    }
}