package com.ecommerce.controller;

import com.ecommerce.dto.command.ProductCreateCommand;
import com.ecommerce.dto.command.ProductSearchCommand;
import com.ecommerce.dto.request.ProductCreateRequest;
import com.ecommerce.dto.request.ProductSearchRequest;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.dto.projection.ProductSearchProjection;
import com.ecommerce.service.ProductService;
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
 * Controlador REST para gestión de productos con patrón Command
 * Todos los endpoints CRUD implementan el patrón Command
 * 
 * @author Developer
 * @version 1.0.0
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Productos", description = "API para gestión de productos con patrón Command completo")
public class ProductController {
    
    private final ProductService productService;
    
    /**
     * ENDPOINT ESPECIAL: Búsqueda avanzada con patrón Command
     */
    @PostMapping("/search")
    @Operation(
        summary = "Búsqueda avanzada de productos (ENDPOINT ESPECIAL)",
        description = "Búsqueda avanzada usando patrón Command, query nativa y proyección a interfaz"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente",
                    content = @Content(schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "400", description = "Parámetros de búsqueda inválidos")
    })
    public ResponseEntity<Page<ProductSearchProjection>> searchProducts(
            @Parameter(description = "Criterios de búsqueda", required = true)
            @Valid @RequestBody ProductSearchRequest searchRequest,
            @Parameter(description = "Parámetros de paginación")
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        
        log.info("REST: Búsqueda avanzada de productos");
        
        // Validación del request
        if (searchRequest == null) {
            throw new IllegalArgumentException("Search request cannot be null");
        }
        
        // Construcción manual del Command usando Builder Pattern
        var searchCommand = ProductSearchCommand.builder()
                .name(searchRequest.getName())
                .description(searchRequest.getDescription())
                .brand(searchRequest.getBrand())
                .model(searchRequest.getModel())
                .minPrice(searchRequest.getMinPrice())
                .maxPrice(searchRequest.getMaxPrice())
                .minStock(searchRequest.getMinStock())
                .maxStock(searchRequest.getMaxStock())
                .active(searchRequest.getActive())
                .featured(searchRequest.getFeatured())
                .minWeight(searchRequest.getMinWeight())
                .maxWeight(searchRequest.getMaxWeight())
                .dimensions(searchRequest.getDimensions())
                .searchText(searchRequest.getSearchText())
                .categoryIds(searchRequest.getCategoryIds())
                .categoryNames(searchRequest.getCategoryNames())
                .sortBy(searchRequest.getSortBy())
                .sortDirection(searchRequest.getSortDirection())
                .build();
        
        Page<ProductSearchProjection> results = productService.searchProducts(searchCommand, pageable);
        return ResponseEntity.ok(results);
    }
    
    @PostMapping
    @Operation(
        summary = "Crear producto",
        description = "Crea un nuevo producto usando patrón Command"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<ProductResponse> createProduct(
            @Parameter(description = "Datos del producto a crear", required = true)
            @Valid @RequestBody ProductCreateRequest request) {
        
        log.info("REST: Creando nuevo producto con nombre: {}", request.getName());
        
        // Validación del request
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        
        // Construcción manual del Command usando Builder Pattern
        var command = ProductCreateCommand.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .imageUrl(request.getImageUrl())
                .brand(request.getBrand())
                .model(request.getModel())
                .weight(request.getWeight())
                .dimensions(request.getDimensions())
                .active(true)
                .featured(request.getFeatured())
                .categoryIds(request.getCategoryIds())
                .build();
        
        ProductResponse response = productService.createProduct(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener producto por ID",
        description = "Obtiene la información detallada de un producto específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "ID del producto", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("REST: Obteniendo producto con ID: {}", id);
        ProductResponse response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(
        summary = "Obtener todos los productos",
        description = "Lista paginada de productos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @Parameter(description = "Parámetros de paginación y ordenamiento")
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        
        log.info("REST: Obteniendo todos los productos con paginación: {}", pageable);
        Page<ProductResponse> response = productService.getAllProducts(pageable);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar producto",
        description = "Actualiza un producto existente usando patrón Command"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "ID del producto a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos del producto", required = true)
            @Valid @RequestBody ProductCreateRequest request) {
        
        log.info("REST: Actualizando producto con ID: {}", id);
        
        // Validación del request
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        
        // Construcción manual del Command usando Builder Pattern
        var command = ProductCreateCommand.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .imageUrl(request.getImageUrl())
                .brand(request.getBrand())
                .model(request.getModel())
                .weight(request.getWeight())
                .dimensions(request.getDimensions())
                .active(true)
                .featured(request.getFeatured())
                .categoryIds(request.getCategoryIds())
                .build();
        
        ProductResponse response = productService.updateProduct(id, command);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar producto",
        description = "Elimina un producto del sistema (soft delete)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID del producto a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("REST: Eliminando producto con ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
} 