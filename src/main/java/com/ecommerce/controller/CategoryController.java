package com.ecommerce.controller;

import com.ecommerce.dto.command.CategoryCreateCommand;
import com.ecommerce.dto.request.CategoryCreateRequest;
import com.ecommerce.dto.response.CategoryResponse;
import com.ecommerce.service.CategoryService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestión de categorías con patrón Command
 * 
 * ENDPOINTS IMPLEMENTADOS (3 de los 6 requeridos):
 * 1. POST /categories - Crear categoría
 * 2. GET /categories/{id} - Obtener categoría por ID  
 * 3. PUT /categories/{id} - Actualizar categoría
 */
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Categorías", description = "API para gestión de categorías de productos con patrón Command")
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @PostMapping
    @Operation(
        summary = "Crear una nueva categoría",
        description = "Crea una nueva categoría de productos usando patrón Command"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "409", description = "El nombre de categoría ya existe")
    })
    public ResponseEntity<CategoryResponse> createCategory(
            @Parameter(description = "Datos de la categoría a crear", required = true)
            @Valid @RequestBody CategoryCreateRequest request) {
        
        log.info("REST: Creando nueva categoría con nombre: {}", request.getName());
        
        // Construcción del Command usando Builder Pattern
        var command = CategoryCreateCommand.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        
        CategoryResponse response = categoryService.createCategory(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener categoría por ID",
        description = "Obtiene la información detallada de una categoría específica por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<CategoryResponse> getCategoryById(
            @Parameter(description = "ID de la categoría", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("REST: Obteniendo categoría con ID: {}", id);
        CategoryResponse response = categoryService.getCategoryById(id);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar categoría",
        description = "Actualiza la información de una categoría existente usando patrón Command"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría actualizada exitosamente",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
        @ApiResponse(responseCode = "409", description = "El nombre de categoría ya existe")
    })
    public ResponseEntity<CategoryResponse> updateCategory(
            @Parameter(description = "ID de la categoría a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos de la categoría", required = true)
            @Valid @RequestBody CategoryCreateRequest request) {
        
        log.info("REST: Actualizando categoría con ID: {}", id);
        
        // Construcción del Command usando Builder Pattern
        var command = CategoryCreateCommand.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        
        CategoryResponse response = categoryService.updateCategory(id, command);
        return ResponseEntity.ok(response);
    }
} 