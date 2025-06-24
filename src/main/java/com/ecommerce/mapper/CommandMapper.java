package com.ecommerce.mapper;

import com.ecommerce.dto.command.CategoryCreateCommand;
import com.ecommerce.dto.command.ProductCreateCommand;
import com.ecommerce.dto.command.ProductSearchCommand;
import com.ecommerce.dto.command.UserCreateCommand;
import com.ecommerce.dto.request.CategoryCreateRequest;
import com.ecommerce.dto.request.ProductCreateRequest;
import com.ecommerce.dto.request.ProductSearchRequest;
import com.ecommerce.dto.request.UserCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper para convertir Requests a Commands
 * Implementa el desacoplamiento siguiendo el patrón Command
 * 
 * @author Developer
 * @version 1.0.0
 */
@Mapper(componentModel = "spring")
public interface CommandMapper {
    
    /**
     * Convierte UserCreateRequest a UserCreateCommand
     */
    UserCreateCommand toCommand(UserCreateRequest request);
    
    /**
     * Convierte ProductCreateRequest a ProductCreateCommand
     */
    ProductCreateCommand toCommand(ProductCreateRequest request);
    
    /**
     * Convierte CategoryCreateRequest a CategoryCreateCommand
     */
    CategoryCreateCommand toCommand(CategoryCreateRequest request);
    
    /**
     * Convierte ProductSearchRequest a ProductSearchCommand
     */
    ProductSearchCommand toCommand(ProductSearchRequest request);
} 