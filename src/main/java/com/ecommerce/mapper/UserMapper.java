package com.ecommerce.mapper;

import com.ecommerce.dto.command.UserCreateCommand;
import com.ecommerce.dto.response.UserResponse;
import com.ecommerce.entity.User;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para la entidad User
 * Actualizado para trabajar con Commands
 * 
 * @author Developer
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    
    /**
     * Convierte UserCreateCommand a User
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "active", constant = "true")
    User toEntity(UserCreateCommand command);
    
    /**
     * Convierte User a UserResponse
     */
    @Mapping(target = "totalOrders", expression = "java(user.getOrders() != null ? user.getOrders().size() : 0)")
    UserResponse toResponse(User user);
    
    /**
     * Actualiza una entidad User existente con datos del Command
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateEntity(UserCreateCommand command, @MappingTarget User user);
    
    /**
     * Convierte lista de User a lista de UserResponse
     */
    List<UserResponse> toResponseList(List<User> users);
} 