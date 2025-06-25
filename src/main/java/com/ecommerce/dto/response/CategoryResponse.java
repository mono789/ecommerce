package com.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * DTO de response para información de categoría
 * 
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Información de respuesta de la categoría")
public class CategoryResponse {
    
    @Schema(description = "ID único de la categoría", example = "1")
    private Long id;
    
    @Schema(description = "Nombre de la categoría", example = "Electrónicos")
    private String name;
    
    @Schema(description = "Descripción de la categoría", example = "Categoría para productos electrónicos")
    private String description;
    
    @Schema(description = "Estado activo de la categoría", example = "true")
    private Boolean active;
    
    @Schema(description = "Fecha de creación", example = "2023-01-15T10:30:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Fecha de última actualización", example = "2023-01-20T14:45:00")
    private LocalDateTime updatedAt;
    
    @Schema(description = "Número de productos en esta categoría", example = "25")
    private Integer productCount;
} 