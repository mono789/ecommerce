package com.ecommerce.service;

import com.ecommerce.dto.command.ProductCreateCommand;
import com.ecommerce.dto.command.ProductSearchCommand;
import com.ecommerce.dto.projection.ProductSearchProjection;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Category;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.HashSet;

/**
 * Servicio para gestión de productos con patrón Command
 * Incluye la búsqueda especial con query nativa y CRUD completo
 * Implementa el patrón Command para desacoplar requests
 * 
 * 
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    
    /**
     * Búsqueda avanzada de productos usando query nativa con countQuery
     * Los resultados se mapean a la interfaz ProductSearchProjection
     * 
     * Este método implementa el patrón Command solicitado:
     * - Recibe un ProductSearchCommand desacoplado del request
     * - Usa paginación con Pageable
     * - Ejecuta query nativa con countQuery en el repository
     * - Mapea automáticamente a interfaz con getters
     */
    @Transactional(readOnly = true)
    public Page<ProductSearchProjection> searchProducts(ProductSearchCommand searchCommand, Pageable pageable) {
        log.info("Ejecutando búsqueda avanzada de productos con criterios: {}", searchCommand);
        
        // Ejecutar la query nativa simplificada con countQuery y mapeo a interfaz
        Page<ProductSearchProjection> results = productRepository.searchProducts(
            searchCommand.getName(),
            searchCommand.getBrand(),
            searchCommand.getMinPrice(),
            searchCommand.getMaxPrice(),
            searchCommand.getMinStock(),
            pageable
        );
        
        log.info("Búsqueda completada. Encontrados {} productos en {} páginas", 
                results.getTotalElements(), results.getTotalPages());
        
        return results;
    }
    
    /**
     * Crea un nuevo producto usando patrón Command
     */
    public ProductResponse createProduct(ProductCreateCommand command) {
        log.info("Creando producto con nombre: {}", command.getName());
        
        // Usar ProductMapper (consistente con UserService y CategoryService)
        Product product = productMapper.toEntityFromCommand(command);
        
        // Agregar categorías si se especificaron
        if (command.getCategoryIds() != null && !command.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(command.getCategoryIds());
            product.setCategories(new HashSet<>(categories));
        }
        
        Product savedProduct = productRepository.save(product);
        
        log.info("Producto creado exitosamente con ID: {}", savedProduct.getId());
        return productMapper.toResponse(savedProduct);
    }
    
    /**
     * Obtiene un producto por ID
     */
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        log.info("Obteniendo producto con ID: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        
        return productMapper.toResponse(product);
    }
    
    /**
     * Obtiene todos los productos con paginación
     */
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        log.info("Obteniendo productos con paginación: {}", pageable);
        
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::toResponse);
    }
    
    /**
     * Actualiza un producto existente usando patrón Command
     */
    public ProductResponse updateProduct(Long id, ProductCreateCommand command) {
        log.info("Actualizando producto con ID: {}", id);
        
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        
        // Usar ProductMapper para actualizar la entidad (consistente con otros servicios)
        productMapper.updateEntityFromCommand(command, existingProduct);
        
        // Actualizar categorías si se especificaron
        if (command.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(command.getCategoryIds());
            existingProduct.setCategories(new HashSet<>(categories));
        }
        
        Product updatedProduct = productRepository.save(existingProduct);
        
        log.info("Producto actualizado exitosamente con ID: {}", updatedProduct.getId());
        return productMapper.toResponse(updatedProduct);
    }
    
    /**
     * Elimina un producto (soft delete)
     */
    public void deleteProduct(Long id) {
        log.info("Eliminando producto con ID: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        
        product.setActive(false);
        productRepository.save(product);
        
        log.info("Producto eliminado exitosamente con ID: {}", id);
    }
} 