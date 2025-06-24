package com.ecommerce.dto.command;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Command para creación de usuarios
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
public class UserCreateCommand {
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private String phone;
    
    private String address;
    
    private String city;
    
    private String country;
} 