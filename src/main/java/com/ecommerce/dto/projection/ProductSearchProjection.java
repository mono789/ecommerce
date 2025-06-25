package com.ecommerce.dto.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Interfaz de proyección para mapear los resultados de la búsqueda nativa de productos
 * Esta interfaz se utiliza para mapear los resultados de la query nativa con countQuery
 * 
 * 
 */
public interface ProductSearchProjection {
    
    /**
     * @return ID del producto
     */
    Long getId();
    
    /**
     * @return Nombre del producto
     */
    String getName();
    
    /**
     * @return Descripción del producto
     */
    String getDescription();
    
    /**
     * @return Precio del producto
     */
    BigDecimal getPrice();
    
    /**
     * @return Stock disponible del producto
     */
    Integer getStock();
    
    /**
     * @return URL de la imagen del producto
     */
    String getImageUrl();
    
    /**
     * @return Marca del producto
     */
    String getBrand();
    
    /**
     * @return Modelo del producto
     */
    String getModel();
    
    /**
     * @return Peso del producto
     */
    BigDecimal getWeight();
    
    /**
     * @return Dimensiones del producto
     */
    String getDimensions();
    
    /**
     * @return Si el producto está activo
     */
    Boolean getActive();
    
    /**
     * @return Si el producto está destacado
     */
    Boolean getFeatured();
    
    /**
     * @return Fecha de creación del producto
     */
    LocalDateTime getCreatedAt();
    
    /**
     * @return Fecha de última actualización del producto
     */
    LocalDateTime getUpdatedAt();
    
    /**
     * @return Nombres de las categorías concatenadas
     */
    String getCategoryNames();
    
    /**
     * @return IDs de las categorías concatenadas
     */
    String getCategoryIds();
} 