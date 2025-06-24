package com.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad que representa un item de una orden de compra
 * 
 * @author Developer
 * @version 1.0.0
 */
@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"order", "product"})
@EqualsAndHashCode(exclude = {"order", "product"})
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio unitario debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio unitario debe tener máximo 10 dígitos enteros y 2 decimales")
    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;
    
    @NotNull(message = "El total del item es obligatorio")
    @DecimalMin(value = "0.0", message = "El total del item debe ser mayor o igual a 0")
    @Digits(integer = 10, fraction = 2, message = "El total del item debe tener máximo 10 dígitos enteros y 2 decimales")
    @Column(name = "total_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;
    
    @DecimalMin(value = "0.0", message = "El descuento debe ser mayor o igual a 0")
    @DecimalMax(value = "100.0", message = "El descuento no puede ser mayor al 100%")
    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;
    
    @DecimalMin(value = "0.0", message = "El monto del descuento debe ser mayor o igual a 0")
    @Digits(integer = 10, fraction = 2, message = "El monto del descuento debe tener máximo 10 dígitos enteros y 2 decimales")
    @Column(name = "discount_amount", precision = 12, scale = 2)
    private BigDecimal discountAmount;
    
    @Size(max = 500, message = "Las notas no pueden exceder 500 caracteres")
    @Column(name = "notes", length = 500)
    private String notes;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Relación Many-to-One con Order
     * Un item pertenece a una orden
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    /**
     * Relación Many-to-One con Product
     * Un item se refiere a un producto específico
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    /**
     * Método para calcular el precio total basado en cantidad y precio unitario
     */
    @PrePersist
    @PreUpdate
    private void calculateTotalPrice() {
        if (quantity != null && unitPrice != null) {
            totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
            
            if (discountAmount != null && discountAmount.compareTo(BigDecimal.ZERO) > 0) {
                totalPrice = totalPrice.subtract(discountAmount);
            } else if (discountPercentage != null && discountPercentage.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal discountValue = totalPrice.multiply(discountPercentage).divide(BigDecimal.valueOf(100));
                totalPrice = totalPrice.subtract(discountValue);
            }
        }
    }
} 