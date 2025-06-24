package com.ecommerce.service;

import com.ecommerce.dto.command.UserCreateCommand;
import com.ecommerce.dto.response.UserResponse;
import com.ecommerce.entity.User;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para gestión de usuarios
 * Implementa el patrón Command para desacoplar requests
 * 
 * @author Developer
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    /**
     * Crea un nuevo usuario usando Command
     */
    public UserResponse createUser(UserCreateCommand command) {
        log.info("Creando usuario con email: {}", command.getEmail());
        
        if (userRepository.existsByEmail(command.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + command.getEmail());
        }
        
        User user = userMapper.toEntity(command);
        User savedUser = userRepository.save(user);
        
        log.info("Usuario creado exitosamente con ID: {}", savedUser.getId());
        return userMapper.toResponse(savedUser);
    }
    
    /**
     * Obtiene un usuario por ID
     */
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        log.info("Obteniendo usuario con ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        
        return userMapper.toResponse(user);
    }
    
    /**
     * Obtiene todos los usuarios con paginación
     */
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        log.info("Obteniendo usuarios con paginación: {}", pageable);
        
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toResponse);
    }
    
    /**
     * Actualiza un usuario existente usando Command
     */
    public UserResponse updateUser(Long id, UserCreateCommand command) {
        log.info("Actualizando usuario con ID: {}", id);
        
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        
        // Verificar si el email ya existe en otro usuario
        if (!existingUser.getEmail().equals(command.getEmail()) && 
            userRepository.existsByEmail(command.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + command.getEmail());
        }
        
        userMapper.updateEntity(command, existingUser);
        User updatedUser = userRepository.save(existingUser);
        
        log.info("Usuario actualizado exitosamente con ID: {}", updatedUser.getId());
        return userMapper.toResponse(updatedUser);
    }
    
    /**
     * Elimina un usuario (soft delete)
     */
    public void deleteUser(Long id) {
        log.info("Eliminando usuario con ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        
        user.setActive(false);
        userRepository.save(user);
        
        log.info("Usuario eliminado exitosamente con ID: {}", id);
    }
    
    /**
     * Busca usuarios por estado activo
     */
    @Transactional(readOnly = true)
    public Page<UserResponse> getUsersByActive(Boolean active, Pageable pageable) {
        log.info("Obteniendo usuarios activos: {} con paginación: {}", active, pageable);
        
        Page<User> users = userRepository.findByActive(active, pageable);
        return users.map(userMapper::toResponse);
    }
    
    /**
     * Busca usuarios por nombre y apellido
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersByName(String firstName, String lastName) {
        log.info("Buscando usuarios por nombre: {} {}", firstName, lastName);
        
        List<User> users = userRepository.findByFirstNameAndLastNameContainingIgnoreCase(firstName, lastName);
        return userMapper.toResponseList(users);
    }
    
    /**
     * Busca usuarios por ciudad
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersByCity(String city) {
        log.info("Buscando usuarios por ciudad: {}", city);
        
        List<User> users = userRepository.findByCityIgnoreCaseAndActiveTrue(city);
        return userMapper.toResponseList(users);
    }
} 