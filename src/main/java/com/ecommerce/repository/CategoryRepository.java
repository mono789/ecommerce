package com.ecommerce.repository;

import com.ecommerce.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Category
 * 
 * 
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * Busca una categoría por nombre
     */
    Optional<Category> findByName(String name);
    
    /**
     * Verifica si existe una categoría con el nombre dado
     */
    boolean existsByName(String name);
    
    /**
     * Busca categorías por estado activo
     */
    Page<Category> findByActive(Boolean active, Pageable pageable);
    
    /**
     * Busca categorías por nombre (case insensitive) que contengan el texto
     */
    List<Category> findByNameContainingIgnoreCaseAndActiveTrue(String name);
    
    /**
     * Busca categorías por descripción (case insensitive) que contengan el texto
     */
    List<Category> findByDescriptionContainingIgnoreCaseAndActiveTrue(String description);
    
    /**
     * Busca todas las categorías activas ordenadas por nombre
     */
    List<Category> findByActiveTrueOrderByNameAsc();
    
    /**
     * Busca categorías con productos
     */
    @Query("SELECT DISTINCT c FROM Category c JOIN c.products p WHERE c.active = true AND p.active = true")
    List<Category> findCategoriesWithActiveProducts();
    
    /**
     * Cuenta la cantidad de productos por categoría
     */
    @Query("SELECT c.id, c.name, COUNT(p) FROM Category c LEFT JOIN c.products p " +
           "WHERE c.active = true GROUP BY c.id, c.name ORDER BY COUNT(p) DESC")
    List<Object[]> findCategoriesWithProductCount();
} 