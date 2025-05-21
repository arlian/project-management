// src/main/java/com/example/project_management/config/OpenApiConfig.java
package com.example.project_management.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * OpenAPI configuration to register a global JWT Bearer security scheme.
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title       = "Project Management API",
        version     = "v1",
        description = "Endpoints for project management"
    ),
    security = @SecurityRequirement(name = "bearerAuth")  // apply globally
)
@SecurityScheme(
    name             = "bearerAuth",                      // must match @SecurityRequirement
    type             = SecuritySchemeType.HTTP,
    scheme           = "bearer",
    bearerFormat     = "JWT",
    description      = "Provide your JWT token here. Example: `eyJhbGciOiJIUzI1Ni...`"
)
public class OpenApiConfig {
    // no methods needed—annotations do the work
}
