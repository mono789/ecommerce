package com.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * DTO de response para información de usuario
 * 
 * @author Developer
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Información de respuesta del usuario")
public class UserResponse {
    
    @Schema(description = "ID único del usuario", example = "1")
    private Long id;
    
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String firstName;
    
    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String lastName;
    
    @Schema(description = "Email del usuario", example = "juan.perez@email.com")
    private String email;
    
    @Schema(description = "Teléfono del usuario", example = "+573001234567")
    private String phone;
    
    @Schema(description = "Dirección del usuario", example = "Carrera 15 # 93-47, Chapinero, Bogotá")
    private String address;
    
    @Schema(description = "Ciudad del usuario", example = "Bogotá")
    private String city;
    
    @Schema(description = "País del usuario", example = "Colombia")
    private String country;
    
    @Schema(description = "Estado activo del usuario", example = "true")
    private Boolean active;
    
    @Schema(description = "Fecha de creación", example = "2023-01-15T10:30:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Fecha de última actualización", example = "2023-01-20T14:45:00")
    private LocalDateTime updatedAt;
    
    @Schema(description = "Número total de órdenes del usuario", example = "5")
    private Integer totalOrders;
} 