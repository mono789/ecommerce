package com.ecommerce.service;

import com.ecommerce.dto.command.CategoryCreateCommand;
import com.ecommerce.dto.response.CategoryResponse;
import com.ecommerce.entity.Category;
import com.ecommerce.mapper.CategoryMapper;
import com.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para gestión de categorías con patrón Command
 * 
 * 
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    
    /**
     * Crea una nueva categoría usando patrón Command
     */
    public CategoryResponse createCategory(CategoryCreateCommand command) {
        log.info("Creando categoría con nombre: {}", command.getName());
        
        if (categoryRepository.existsByName(command.getName())) {
            throw new RuntimeException("Ya existe una categoría con el nombre: " + command.getName());
        }
        
        Category category = categoryMapper.toEntityFromCommand(command);
        Category savedCategory = categoryRepository.save(category);
        
        log.info("Categoría creada exitosamente con ID: {}", savedCategory.getId());
        return categoryMapper.toResponse(savedCategory);
    }
    
    /**
     * Obtiene una categoría por ID
     */
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        log.info("Obteniendo categoría con ID: {}", id);
        
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        
        return categoryMapper.toResponse(category);
    }
    
    /**
     * Obtiene todas las categorías con paginación
     */
    @Transactional(readOnly = true)
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        log.info("Obteniendo categorías con paginación: {}", pageable);
        
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(categoryMapper::toResponse);
    }
    
    /**
     * Actualiza una categoría existente usando patrón Command
     */
    public CategoryResponse updateCategory(Long id, CategoryCreateCommand command) {
        log.info("Actualizando categoría con ID: {}", id);
        
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        
        // Verificar si el nombre ya existe en otra categoría
        if (!existingCategory.getName().equals(command.getName()) && 
            categoryRepository.existsByName(command.getName())) {
            throw new RuntimeException("Ya existe una categoría con el nombre: " + command.getName());
        }
        
        categoryMapper.updateEntityFromCommand(command, existingCategory);
        Category updatedCategory = categoryRepository.save(existingCategory);
        
        log.info("Categoría actualizada exitosamente con ID: {}", updatedCategory.getId());
        return categoryMapper.toResponse(updatedCategory);
    }
    
    /**
     * Elimina una categoría (soft delete)
     */
    public void deleteCategory(Long id) {
        log.info("Eliminando categoría con ID: {}", id);
        
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        
        category.setActive(false);
        categoryRepository.save(category);
        
        log.info("Categoría eliminada exitosamente con ID: {}", id);
    }
    
    /**
     * Busca categorías activas
     */
    @Transactional(readOnly = true)
    public List<CategoryResponse> getActiveCategories() {
        log.info("Obteniendo categorías activas");
        
        List<Category> categories = categoryRepository.findByActiveTrueOrderByNameAsc();
        return categoryMapper.toResponseList(categories);
    }
} 