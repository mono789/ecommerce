package com.ecommerce.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de request para crear un nuevo usuario
 * 
 * @author Developer
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos requeridos para crear un nuevo usuario")
public class UserCreateRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Schema(description = "Nombre del usuario", example = "Juan", required = true)
    private String firstName;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    @Schema(description = "Apellido del usuario", example = "Pérez", required = true)
    private String lastName;
    
    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es obligatorio")
    @Schema(description = "Email del usuario", example = "juan.perez@email.com", required = true)
    private String email;
    
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "El teléfono debe tener un formato válido")
    @Schema(description = "Teléfono del usuario", example = "+52123456789", required = true)
    private String phone;
    
    @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    @Schema(description = "Dirección del usuario", example = "Av. Principal 123, Col. Centro")
    private String address;
    
    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    @Schema(description = "Ciudad del usuario", example = "Ciudad de México")
    private String city;
    
    @Size(max = 100, message = "El país no puede exceder 100 caracteres")
    @Schema(description = "País del usuario", example = "México")
    private String country;
} 