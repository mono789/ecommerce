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
 * @author Developer
 * @version 1.0.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Búsqueda avanzada de productos usando query nativa con countQuery
     * Los resultados se mapean a la interfaz ProductSearchProjection
     * Este es el endpoint especial solicitado con el patrón request
     * Optimizado para PostgreSQL con STRING_AGG
     */
    @Query(value = """
        SELECT DISTINCT 
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
            STRING_AGG(DISTINCT c.name, ', ') as categoryNames,
            STRING_AGG(DISTINCT c.category_id::text, ', ') as categoryIds,
            CASE 
                WHEN :searchText IS NULL OR :searchText = '' THEN 0.0
                ELSE (
                    (CASE WHEN LOWER(p.name) LIKE LOWER(CONCAT('%', :searchText, '%')) THEN 3.0 ELSE 0.0 END) +
                    (CASE WHEN LOWER(p.description) LIKE LOWER(CONCAT('%', :searchText, '%')) THEN 2.0 ELSE 0.0 END) +
                    (CASE WHEN LOWER(p.brand) LIKE LOWER(CONCAT('%', :searchText, '%')) THEN 2.5 ELSE 0.0 END) +
                    (CASE WHEN LOWER(p.model) LIKE LOWER(CONCAT('%', :searchText, '%')) THEN 2.0 ELSE 0.0 END) +
                    (CASE WHEN LOWER(c.name) LIKE LOWER(CONCAT('%', :searchText, '%')) THEN 1.5 ELSE 0.0 END)
                )
            END as relevanceScore
        FROM products p
        LEFT JOIN product_categories pc ON p.product_id = pc.product_id
        LEFT JOIN categories c ON pc.category_id = c.category_id
        WHERE 1=1
            AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
            AND (:description IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%')))
            AND (:brand IS NULL OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :brand, '%')))
            AND (:model IS NULL OR LOWER(p.model) LIKE LOWER(CONCAT('%', :model, '%')))
            AND (:minPrice IS NULL OR p.price >= :minPrice)
            AND (:maxPrice IS NULL OR p.price <= :maxPrice)
            AND (:minStock IS NULL OR p.stock >= :minStock)
            AND (:maxStock IS NULL OR p.stock <= :maxStock)
            AND (:active IS NULL OR p.active = :active)
            AND (:featured IS NULL OR p.featured = :featured)
            AND (:minWeight IS NULL OR p.weight >= :minWeight)
            AND (:maxWeight IS NULL OR p.weight <= :maxWeight)
            AND (:dimensions IS NULL OR LOWER(p.dimensions) LIKE LOWER(CONCAT('%', :dimensions, '%')))
            AND (:searchText IS NULL OR :searchText = '' OR 
                 LOWER(p.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR
                 LOWER(p.description) LIKE LOWER(CONCAT('%', :searchText, '%')) OR
                 LOWER(p.brand) LIKE LOWER(CONCAT('%', :searchText, '%')) OR
                 LOWER(p.model) LIKE LOWER(CONCAT('%', :searchText, '%')) OR
                 LOWER(c.name) LIKE LOWER(CONCAT('%', :searchText, '%')))
            AND (:categoryIds IS NULL OR c.category_id = ANY(CAST(:categoryIds AS bigint[])))
            AND (:categoryNames IS NULL OR LOWER(c.name) = ANY(SELECT LOWER(UNNEST(CAST(:categoryNames AS text[])))))
        GROUP BY p.product_id, p.name, p.description, p.price, p.stock, p.image_url, 
                 p.brand, p.model, p.weight, p.dimensions, p.active, p.featured, 
                 p.created_at, p.updated_at
        ORDER BY 
            CASE 
                WHEN :sortBy = 'name' AND :sortDirection = 'asc' THEN p.name
                WHEN :sortBy = 'brand' AND :sortDirection = 'asc' THEN p.brand
                WHEN :sortBy = 'model' AND :sortDirection = 'asc' THEN p.model
            END ASC,
            CASE 
                WHEN :sortBy = 'name' AND :sortDirection = 'desc' THEN p.name
                WHEN :sortBy = 'brand' AND :sortDirection = 'desc' THEN p.brand
                WHEN :sortBy = 'model' AND :sortDirection = 'desc' THEN p.model
            END DESC,
            CASE 
                WHEN :sortBy = 'price' AND :sortDirection = 'asc' THEN p.price
                WHEN :sortBy = 'stock' AND :sortDirection = 'asc' THEN p.stock
                WHEN :sortBy = 'weight' AND :sortDirection = 'asc' THEN p.weight
            END ASC,
            CASE 
                WHEN :sortBy = 'price' AND :sortDirection = 'desc' THEN p.price
                WHEN :sortBy = 'stock' AND :sortDirection = 'desc' THEN p.stock
                WHEN :sortBy = 'weight' AND :sortDirection = 'desc' THEN p.weight
            END DESC,
            CASE 
                WHEN :sortBy = 'createdAt' AND :sortDirection = 'asc' THEN p.created_at
            END ASC,
            CASE 
                WHEN :sortBy = 'createdAt' AND :sortDirection = 'desc' THEN p.created_at
            END DESC,
            CASE 
                WHEN :searchText IS NOT NULL AND :searchText != '' THEN relevanceScore
            END DESC,
            p.created_at DESC
        """,
        countQuery = """
        SELECT COUNT(DISTINCT p.product_id)
        FROM products p
        LEFT JOIN product_categories pc ON p.product_id = pc.product_id
        LEFT JOIN categories c ON pc.category_id = c.category_id
        WHERE 1=1
            AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
            AND (:description IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%')))
            AND (:brand IS NULL OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :brand, '%')))
            AND (:model IS NULL OR LOWER(p.model) LIKE LOWER(CONCAT('%', :model, '%')))
            AND (:minPrice IS NULL OR p.price >= :minPrice)
            AND (:maxPrice IS NULL OR p.price <= :maxPrice)
            AND (:minStock IS NULL OR p.stock >= :minStock)
            AND (:maxStock IS NULL OR p.stock <= :maxStock)
            AND (:active IS NULL OR p.active = :active)
            AND (:featured IS NULL OR p.featured = :featured)
            AND (:minWeight IS NULL OR p.weight >= :minWeight)
            AND (:maxWeight IS NULL OR p.weight <= :maxWeight)
            AND (:dimensions IS NULL OR LOWER(p.dimensions) LIKE LOWER(CONCAT('%', :dimensions, '%')))
            AND (:searchText IS NULL OR :searchText = '' OR 
                 LOWER(p.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR
                 LOWER(p.description) LIKE LOWER(CONCAT('%', :searchText, '%')) OR
                 LOWER(p.brand) LIKE LOWER(CONCAT('%', :searchText, '%')) OR
                 LOWER(p.model) LIKE LOWER(CONCAT('%', :searchText, '%')) OR
                 LOWER(c.name) LIKE LOWER(CONCAT('%', :searchText, '%')))
            AND (:categoryIds IS NULL OR c.category_id = ANY(CAST(:categoryIds AS bigint[])))
            AND (:categoryNames IS NULL OR LOWER(c.name) = ANY(SELECT LOWER(UNNEST(CAST(:categoryNames AS text[])))))
        """,
        nativeQuery = true)
    Page<ProductSearchProjection> searchProducts(
            @Param("name") String name,
            @Param("description") String description,
            @Param("brand") String brand,
            @Param("model") String model,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minStock") Integer minStock,
            @Param("maxStock") Integer maxStock,
            @Param("active") Boolean active,
            @Param("featured") Boolean featured,
            @Param("minWeight") BigDecimal minWeight,
            @Param("maxWeight") BigDecimal maxWeight,
            @Param("dimensions") String dimensions,
            @Param("searchText") String searchText,
            @Param("categoryIds") List<Long> categoryIds,
            @Param("categoryNames") List<String> categoryNames,
            @Param("sortBy") String sortBy,
            @Param("sortDirection") String sortDirection,
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