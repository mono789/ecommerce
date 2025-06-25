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
 * DTO de request para búsqueda avanzada de productos con filtros múltiples
 * Este es el request especial que se usará con POST para filtrar por varios atributos
 * 
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Criterios de búsqueda avanzada para productos")
public class ProductSearchRequest {
    
    @Schema(description = "Nombre o parte del nombre del producto", example = "iPhone")
    private String name;
    
    @Schema(description = "Descripción o parte de la descripción del producto", example = "smartphone")
    private String description;
    
    @Schema(description = "Marca del producto", example = "Apple")
    private String brand;
    
    @Schema(description = "Modelo del producto", example = "iPhone 14")
    private String model;
    
    @DecimalMin(value = "0.0", message = "El precio mínimo debe ser mayor o igual a 0")
    @Schema(description = "Precio mínimo del producto", example = "100.00")
    private BigDecimal minPrice;
    
    @DecimalMin(value = "0.0", message = "El precio máximo debe ser mayor o igual a 0")
    @Schema(description = "Precio máximo del producto", example = "1000.00")
    private BigDecimal maxPrice;
    
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    @Schema(description = "Stock mínimo disponible", example = "1")
    private Integer minStock;
    
    @Min(value = 0, message = "El stock máximo no puede ser negativo")
    @Schema(description = "Stock máximo disponible", example = "100")
    private Integer maxStock;
    
    @Schema(description = "IDs de categorías a las que debe pertenecer el producto", example = "[1, 2, 3]")
    private List<Long> categoryIds;
    
    @Schema(description = "Nombres de categorías a las que debe pertenecer el producto", example = "[\"Electrónicos\", \"Smartphones\"]")
    private List<String> categoryNames;
    
    @Schema(description = "Si el producto debe estar activo", example = "true")
    private Boolean active;
    
    @Schema(description = "Si el producto debe estar destacado", example = "true")
    private Boolean featured;
    
    @DecimalMin(value = "0.0", message = "El peso mínimo debe ser mayor o igual a 0")
    @Schema(description = "Peso mínimo del producto en kg", example = "0.1")
    private BigDecimal minWeight;
    
    @DecimalMin(value = "0.0", message = "El peso máximo debe ser mayor o igual a 0")
    @Schema(description = "Peso máximo del producto en kg", example = "2.0")
    private BigDecimal maxWeight;
    
    @Schema(description = "Dimensiones del producto", example = "15x8x1 cm")
    private String dimensions;
    
    @Schema(description = "Ordenamiento de los resultados", 
            example = "name", 
            allowableValues = {"name", "price", "stock", "createdAt", "brand", "model"})
    private String sortBy;
    
    @Schema(description = "Dirección del ordenamiento", 
            example = "asc", 
            allowableValues = {"asc", "desc"})
    private String sortDirection;
    
    @Schema(description = "Texto de búsqueda general que busca en nombre, descripción, marca y modelo", 
            example = "smartphone apple")
    private String searchText;
    
    @Min(value = 0, message = "El número de página no puede ser negativo")
    @Schema(description = "Número de página (base 0)", example = "0")
    private Integer page;
    
    @Min(value = 1, message = "El tamaño de página debe ser mayor a 0")
    @Max(value = 100, message = "El tamaño de página no puede ser mayor a 100")
    @Schema(description = "Tamaño de página", example = "10")
    private Integer size;
} 