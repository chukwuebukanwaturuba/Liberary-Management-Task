package com.ebuka.librarymanagementsystem.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration

public class SwaggerConfig {
    @Value("${app.version}")
    private String version;

    @Bean
    public OpenAPI api() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("jwt", securityScheme))
                .info(new Info()
                        .title("Library Management System API")
                        .description("API that provides CRUD operations for Library Management System.")
                        .version(version))
                .security(Collections.singletonList(new SecurityRequirement().addList("jwt")));
    }

    @Bean
    public GroupedOpenApi authEndpoint() {
        return GroupedOpenApi.builder()
                .group("Auth")
                .pathsToMatch("/api/v1/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi patronEndpoint() {
        return GroupedOpenApi.builder()
                .group("Patron")
                .pathsToMatch("/api/v1/patron/**")
                .build();
    }
    @Bean
    public GroupedOpenApi bookEndpoint() {
        return GroupedOpenApi.builder()
                .group("Book")
                .pathsToMatch("/api/v1/book/**")
                .build();
    }
    @Bean
    public GroupedOpenApi borrowEndpoint() {
        return GroupedOpenApi.builder()
                .group("Borrow")
                .pathsToMatch("/api/v1/borrow/**")
                .build();}
}
