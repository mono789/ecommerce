package com.ecommerce.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO de request para crear un nuevo producto
 * 
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos requeridos para crear un nuevo producto")
public class ProductCreateRequest {
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 200, message = "El nombre debe tener entre 2 y 200 caracteres")
    @Schema(description = "Nombre del producto", example = "iPhone 14 Pro", required = true)
    private String name;
    
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    @Schema(description = "Descripción del producto", example = "Smartphone Apple con tecnología avanzada")
    private String description;
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener máximo 10 dígitos enteros y 2 decimales")
    @Schema(description = "Precio del producto", example = "999000", required = true)
    private BigDecimal price;
    
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Schema(description = "Stock inicial del producto", example = "50", required = true)
    private Integer stock;
    
    @Size(max = 500, message = "La URL de imagen no puede exceder 500 caracteres")
    @Schema(description = "URL de la imagen del producto", example = "https://example.com/iphone14.jpg")
    private String imageUrl;
    
    @Size(max = 50, message = "La marca no puede exceder 50 caracteres")
    @Schema(description = "Marca del producto", example = "Apple")
    private String brand;
    
    @Size(max = 100, message = "El modelo no puede exceder 100 caracteres")
    @Schema(description = "Modelo del producto", example = "iPhone 14 Pro")
    private String model;
    
    @DecimalMin(value = "0.0", message = "El peso debe ser mayor o igual a 0")
    @Schema(description = "Peso del producto en kg", example = "0.206")
    private BigDecimal weight;
    
    @Size(max = 100, message = "Las dimensiones no pueden exceder 100 caracteres")
    @Schema(description = "Dimensiones del producto", example = "15.4 x 7.3 x 0.8 cm")
    private String dimensions;
    
    @Schema(description = "Si el producto está destacado", example = "false")
    private Boolean featured;
    
    @Schema(description = "IDs de las categorías del producto", example = "[1, 2, 3]")
    private List<Long> categoryIds;
} 