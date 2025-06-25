package com.ecommerce.dto.command;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Command para creación de productos
 * Desacopla el request del servicio siguiendo el patrón Command
 */
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateCommand {
    
    private String name;
    
    private String description;
    
    private BigDecimal price;
    
    private Integer stock;
    
    private String imageUrl;
    
    private String brand;
    
    private String model;
    
    private BigDecimal weight;
    
    private String dimensions;
    
    private Boolean active;
    
    private Boolean featured;
    
    private List<Long> categoryIds;
} 