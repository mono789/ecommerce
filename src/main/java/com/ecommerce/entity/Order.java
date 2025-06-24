package com.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una orden de compra
 * 
 * @author Developer
 * @version 1.0.0
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user", "orderItems"})
@EqualsAndHashCode(exclude = {"user", "orderItems"})
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    
    @NotBlank(message = "El número de orden es obligatorio")
    @Size(max = 50, message = "El número de orden no puede exceder 50 caracteres")
    @Column(name = "order_number", nullable = false, unique = true, length = 50)
    private String orderNumber;
    
    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.0", message = "El total debe ser mayor o igual a 0")
    @Digits(integer = 10, fraction = 2, message = "El total debe tener máximo 10 dígitos enteros y 2 decimales")
    @Column(name = "total", nullable = false, precision = 12, scale = 2)
    private BigDecimal total;
    
    @DecimalMin(value = "0.0", message = "El subtotal debe ser mayor o igual a 0")
    @Digits(integer = 10, fraction = 2, message = "El subtotal debe tener máximo 10 dígitos enteros y 2 decimales")
    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal;
    
    @DecimalMin(value = "0.0", message = "Los impuestos deben ser mayor o igual a 0")
    @Digits(integer = 10, fraction = 2, message = "Los impuestos debe tener máximo 10 dígitos enteros y 2 decimales")
    @Column(name = "taxes", precision = 12, scale = 2)
    private BigDecimal taxes;
    
    @DecimalMin(value = "0.0", message = "El costo de envío debe ser mayor o igual a 0")
    @Digits(integer = 10, fraction = 2, message = "El costo de envío debe tener máximo 10 dígitos enteros y 2 decimales")
    @Column(name = "shipping_cost", precision = 12, scale = 2)
    private BigDecimal shippingCost;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;
    
    @Size(max = 500, message = "La dirección de envío no puede exceder 500 caracteres")
    @Column(name = "shipping_address", length = 500)
    private String shippingAddress;
    
    @Size(max = 500, message = "La dirección de facturación no puede exceder 500 caracteres")
    @Column(name = "billing_address", length = 500)
    private String billingAddress;
    
    @Size(max = 1000, message = "Las notas no pueden exceder 1000 caracteres")
    @Column(name = "notes", length = 1000)
    private String notes;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "shipped_at")
    private LocalDateTime shippedAt;
    
    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;
    
    /**
     * Relación Many-to-One con User
     * Una orden pertenece a un usuario
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    /**
     * Relación One-to-Many con OrderItems
     * Una orden puede tener múltiples items
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();
    
    /**
     * Enum para el estado de la orden
     */
    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        PROCESSING,
        SHIPPED,
        DELIVERED,
        CANCELLED,
        REFUNDED
    }
} 