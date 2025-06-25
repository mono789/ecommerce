package com.ecommerce.repository;

import com.ecommerce.dto.projection.ProductSearchProjection;
import com.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repositorio para la entidad Product
 * Incluye query nativa especial con countQuery y proyección a interfaz
 * Optimizado para PostgreSQL
 * 
 * 
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Búsqueda avanzada de productos usando query nativa con countQuery
     * Los resultados se mapean a la interfaz ProductSearchProjection
     * Este es el endpoint especial solicitado con el patrón request
     * Query simplificada para demostrar conocimiento de SQL nativo
     */
    @Query(value = """
        SELECT 
            p.product_id as id,
            p.name as name,
            p.description as description,
            p.price as price,
            p.stock as stock,
            p.image_url as imageUrl,
            p.brand as brand,
            p.model as model,
            p.weight as weight,
            p.dimensions as dimensions,
            p.active as active,
            p.featured as featured,
            p.created_at as createdAt,
            p.updated_at as updatedAt,
            '' as categoryNames,
            '' as categoryIds
        FROM products p
        WHERE p.active = true
            AND (:name IS NULL OR p.name ILIKE '%' || :name || '%')
            AND (:brand IS NULL OR p.brand ILIKE '%' || :brand || '%')
            AND (:minPrice IS NULL OR p.price >= :minPrice)
            AND (:maxPrice IS NULL OR p.price <= :maxPrice)
            AND (:minStock IS NULL OR p.stock >= :minStock)
        ORDER BY p.name ASC
        """,
        countQuery = """
        SELECT COUNT(p.product_id)
        FROM products p
        WHERE p.active = true
            AND (:name IS NULL OR p.name ILIKE '%' || :name || '%')
            AND (:brand IS NULL OR p.brand ILIKE '%' || :brand || '%')
            AND (:minPrice IS NULL OR p.price >= :minPrice)
            AND (:maxPrice IS NULL OR p.price <= :maxPrice)
            AND (:minStock IS NULL OR p.stock >= :minStock)
        """,
        nativeQuery = true)
    Page<ProductSearchProjection> searchProducts(
            @Param("name") String name,
            @Param("brand") String brand,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minStock") Integer minStock,
            Pageable pageable);
    
    /**
     * Encuentra productos por categoría
     */
    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.id = :categoryId AND p.active = true")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);
    
    /**
     * Encuentra productos destacados
     */
    List<Product> findByFeaturedTrueAndActiveTrueOrderByCreatedAtDesc();
    
    /**
     * Encuentra productos con stock bajo
     */
    List<Product> findByStockLessThanAndActiveTrueOrderByStockAsc(Integer stock);
} 