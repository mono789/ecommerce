package com.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entidad que representa un producto del e-commerce
 * 
 * @author Developer
 * @version 1.0.0
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"categories", "orderItems"})
@EqualsAndHashCode(exclude = {"categories", "orderItems"})
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 200, message = "El nombre debe tener entre 2 y 200 caracteres")
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    @Column(name = "description", length = 1000)
    private String description;
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener máximo 10 dígitos enteros y 2 decimales")
    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
    
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "stock", nullable = false)
    private Integer stock;
    
    @Size(max = 500, message = "La URL de imagen no puede exceder 500 caracteres")
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    @Size(max = 50, message = "La marca no puede exceder 50 caracteres")
    @Column(name = "brand", length = 50)
    private String brand;
    
    @Size(max = 100, message = "El modelo no puede exceder 100 caracteres")
    @Column(name = "model", length = 100)
    private String model;
    
    @DecimalMin(value = "0.0", message = "El peso debe ser mayor o igual a 0")
    @Column(name = "weight", precision = 8, scale = 3)
    private BigDecimal weight;
    
    @Size(max = 100, message = "Las dimensiones no pueden exceder 100 caracteres")
    @Column(name = "dimensions", length = 100)
    private String dimensions;
    
    @Builder.Default
    @Column(name = "active", nullable = false)
    private Boolean active = true;
    
    @Builder.Default
    @Column(name = "featured", nullable = false)
    private Boolean featured = false;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Relación Many-to-Many con Categories
     * Un producto puede pertenecer a múltiples categorías
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "product_categories",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Builder.Default
    private Set<Category> categories = new HashSet<>();
    
    /**
     * Relación One-to-Many con OrderItems
     * Un producto puede estar en múltiples items de orden
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();
} 