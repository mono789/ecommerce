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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de categorías con patrón Command
 * 
 * Flujo implementado:
 * 1. CategoryCreateRequest (entrada)
 * 2. CategoryCreateCommand.builder() (construcción manual)
 * 3. CategoryService.createCategory(command)
 * 4. CategoryResponse (salida)
 * 
 * @author Developer
 * @version 1.0.0
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
        
        // Validación del request
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        
        // Construcción manual del Command usando Builder Pattern
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
    
    @GetMapping
    @Operation(
        summary = "Obtener todas las categorías",
        description = "Obtiene una lista paginada de todas las categorías del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(
            @Parameter(description = "Parámetros de paginación y ordenamiento")
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        
        log.info("REST: Obteniendo todas las categorías con paginación: {}", pageable);
        Page<CategoryResponse> response = categoryService.getAllCategories(pageable);
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
        
        // Validación del request
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        
        // Construcción manual del Command usando Builder Pattern
        var command = CategoryCreateCommand.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        
        CategoryResponse response = categoryService.updateCategory(id, command);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar categoría",
        description = "Realiza una eliminación lógica (soft delete) de la categoría especificada"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID de la categoría a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("REST: Eliminando categoría con ID: {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/active")
    @Operation(
        summary = "Obtener categorías activas",
        description = "Obtiene todas las categorías activas ordenadas por nombre"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorías activas obtenidas exitosamente")
    })
    public ResponseEntity<List<CategoryResponse>> getActiveCategories() {
        
        log.info("REST: Obteniendo categorías activas");
        List<CategoryResponse> response = categoryService.getActiveCategories();
        return ResponseEntity.ok(response);
    }
} 