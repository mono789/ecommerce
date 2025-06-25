package com.ecommerce.mapper;

import com.ecommerce.dto.command.ProductCreateCommand;
import com.ecommerce.dto.request.ProductCreateRequest;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.entity.Product;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper de MapStruct para la entidad Product
 * Soporta tanto Request como Command patterns
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CategoryMapper.class})
public interface ProductMapper {
    
    /**
     * Convierte un ProductCreateRequest a entidad Product
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "featured", defaultValue = "false")
    Product toEntity(ProductCreateRequest request);
    
    /**
     * Convierte un ProductCreateCommand a entidad Product
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "active", defaultValue = "true")
    @Mapping(target = "featured", defaultValue = "false")
    Product toEntityFromCommand(ProductCreateCommand command);
    
    /**
     * Convierte una entidad Product a ProductResponse
     */
    @Mapping(target = "categories", source = "categories")
    ProductResponse toResponse(Product product);
    
    /**
     * Convierte una lista de entidades Product a lista de ProductResponse
     */
    List<ProductResponse> toResponseList(List<Product> products);
    
    /**
     * Actualiza una entidad Product existente con los datos de ProductCreateRequest
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateEntity(ProductCreateRequest request, @MappingTarget Product product);
    
    /**
     * Actualiza una entidad Product existente con los datos de ProductCreateCommand
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateEntityFromCommand(ProductCreateCommand command, @MappingTarget Product product);
} 