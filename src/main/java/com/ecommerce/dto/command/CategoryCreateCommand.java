package com.ecommerce.dto.command;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Command para creación de categorías
 * Desacopla el request del servicio siguiendo el patrón Command
 * Sigue la estructura exacta del ejemplo del entrevistador
 * 
 * @author Developer
 * @version 1.0.0
 */
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCreateCommand {
    
    private String name;
    
    private String description;
    
    private String imageUrl;
    
    private Boolean active;
} 