package com.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de response para información de producto
 * 
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Información de respuesta del producto")
public class ProductResponse {
    
    @Schema(description = "ID único del producto", example = "1")
    private Long id;
    
    @Schema(description = "Nombre del producto", example = "iPhone 14 Pro")
    private String name;
    
    @Schema(description = "Descripción del producto", example = "Smartphone Apple con tecnología avanzada")
    private String description;
    
    @Schema(description = "Precio del producto", example = "999000")
    private BigDecimal price;
    
    @Schema(description = "Stock disponible", example = "50")
    private Integer stock;
    
    @Schema(description = "URL de la imagen del producto", example = "https://example.com/iphone14.jpg")
    private String imageUrl;
    
    @Schema(description = "Marca del producto", example = "Apple")
    private String brand;
    
    @Schema(description = "Modelo del producto", example = "iPhone 14 Pro")
    private String model;
    
    @Schema(description = "Peso del producto en kg", example = "0.206")
    private BigDecimal weight;
    
    @Schema(description = "Dimensiones del producto", example = "15.4 x 7.3 x 0.8 cm")
    private String dimensions;
    
    @Schema(description = "Estado activo del producto", example = "true")
    private Boolean active;
    
    @Schema(description = "Producto destacado", example = "true")
    private Boolean featured;
    
    @Schema(description = "Fecha de creación", example = "2023-01-15T10:30:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Fecha de última actualización", example = "2023-01-20T14:45:00")
    private LocalDateTime updatedAt;
    
    @Schema(description = "Categorías del producto")
    private List<CategoryResponse> categories;
} 