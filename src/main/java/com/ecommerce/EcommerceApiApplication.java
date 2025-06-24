package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;

/**
 * Aplicación principal del API de E-commerce
 * 
 * @author Developer
 * @version 1.0.0
 */
@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "E-commerce API",
        version = "1.0.0",
        description = "API profesional para gestión de e-commerce con funcionalidades avanzadas de búsqueda",
        contact = @Contact(
            name = "Development Team",
            email = "dev@ecommerce.com"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    )
)
public class EcommerceApiApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApiApplication.class, args);
    }
} 