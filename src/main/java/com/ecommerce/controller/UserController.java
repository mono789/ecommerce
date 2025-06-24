package com.ecommerce.controller;

import com.ecommerce.dto.command.UserCreateCommand;
import com.ecommerce.dto.request.UserCreateRequest;
import com.ecommerce.dto.response.UserResponse;
import com.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controlador REST para gestión de usuarios
 * Implementa el patrón Command para desacoplar requests
 * 
 * @author Developer
 * @version 1.0.0
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Usuarios", description = "API para gestión de usuarios del sistema")
public class UserController {
    
    private final UserService userService;    
    @PostMapping
    @Operation(
        summary = "Crear un nuevo usuario",
        description = """
            Crea un nuevo usuario en el sistema con la información proporcionada.
            Utiliza el patrón Command: Request → Command → Service
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "409", description = "El email ya existe en el sistema")
    })
    public ResponseEntity<UserResponse> createUser(
            @Parameter(description = "Datos del usuario a crear", required = true)
            @Valid @RequestBody UserCreateRequest request) {
        
        log.info("REST: Creando nuevo usuario con email: {}", request.getEmail());
        
        // PATRÓN COMMAND: Construir Command usando Builder (como en el ejemplo del entrevistador)
        var command = UserCreateCommand.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .city(request.getCity())
                .country(request.getCountry())
                .build();
        
        log.debug("Command construido: {}", command);
        
        UserResponse response = userService.createUser(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener usuario por ID",
        description = "Obtiene la información detallada de un usuario específico por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("REST: Obteniendo usuario con ID: {}", id);
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Obtiene una lista paginada de todos los usuarios del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @Parameter(description = "Parámetros de paginación y ordenamiento")
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        
        log.info("REST: Obteniendo todos los usuarios con paginación: {}", pageable);
        Page<UserResponse> response = userService.getAllUsers(pageable);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar usuario",
        description = """
            Actualiza la información de un usuario existente.
            Utiliza el patrón Command: Request → Command → Service
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "409", description = "El email ya existe en el sistema")
    })
    public ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "ID del usuario a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos del usuario", required = true)
            @Valid @RequestBody UserCreateRequest request) {
        
        log.info("REST: Actualizando usuario con ID: {}", id);
        
        // PATRÓN COMMAND: Construir Command usando Builder (como en el ejemplo del entrevistador)
        var command = UserCreateCommand.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .city(request.getCity())
                .country(request.getCountry())
                .build();
        
        log.debug("Command construido para actualización: {}", command);
        
        UserResponse response = userService.updateUser(id, command);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar usuario",
        description = "Realiza una eliminación lógica (soft delete) del usuario especificado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID del usuario a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("REST: Eliminando usuario con ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search/by-active")
    @Operation(
        summary = "Buscar usuarios por estado",
        description = "Busca usuarios filtrando por su estado activo/inactivo"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<UserResponse>> getUsersByActive(
            @Parameter(description = "Estado activo del usuario", required = true, example = "true")
            @RequestParam Boolean active,
            @Parameter(description = "Parámetros de paginación y ordenamiento")
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        
        log.info("REST: Buscando usuarios por estado activo: {}", active);
        Page<UserResponse> response = userService.getUsersByActive(active, pageable);
        return ResponseEntity.ok(response);
    }
} 