package com.ecommerce.mapper;

import com.ecommerce.dto.command.CategoryCreateCommand;
import com.ecommerce.dto.request.CategoryCreateRequest;
import com.ecommerce.dto.response.CategoryResponse;
import com.ecommerce.entity.Category;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper de MapStruct para la entidad Category
 * Soporta tanto Request como Command patterns
 * 
 * @author Developer
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    
    /**
     * Convierte un CategoryCreateRequest a entidad Category
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "active", constant = "true")
    Category toEntity(CategoryCreateRequest request);
    
    /**
     * Convierte un CategoryCreateCommand a entidad Category
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "active", constant = "true")
    Category toEntityFromCommand(CategoryCreateCommand command);
    
    /**
     * Convierte una entidad Category a CategoryResponse
     */
    @Mapping(target = "productCount", expression = "java(category.getProducts() != null ? category.getProducts().size() : 0)")
    CategoryResponse toResponse(Category category);
    
    /**
     * Convierte una lista de entidades Category a lista de CategoryResponse
     */
    List<CategoryResponse> toResponseList(List<Category> categories);
    
    /**
     * Actualiza una entidad Category existente con los datos de CategoryCreateRequest
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateEntity(CategoryCreateRequest request, @MappingTarget Category category);
    
    /**
     * Actualiza una entidad Category existente con los datos de CategoryCreateCommand
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateEntityFromCommand(CategoryCreateCommand command, @MappingTarget Category category);
} 