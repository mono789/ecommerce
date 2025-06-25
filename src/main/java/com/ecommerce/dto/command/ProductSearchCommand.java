package com.ecommerce.dto.command;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Command para búsqueda avanzada de productos
 * Desacopla el request del servicio siguiendo el patrón Command
 */
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSearchCommand {
    
    private String name;
    
    private String description;
    
    private String brand;
    
    private String model;
    
    private BigDecimal minPrice;
    
    private BigDecimal maxPrice;
    
    private Integer minStock;
    
    private Integer maxStock;
    
    private Boolean active;
    
    private Boolean featured;
    
    private BigDecimal minWeight;
    
    private BigDecimal maxWeight;
    
    private String dimensions;
    
    private String searchText;
    
    private List<Long> categoryIds;
    
    private List<String> categoryNames;
    
    private String sortBy;
    
    private String sortDirection;
} 