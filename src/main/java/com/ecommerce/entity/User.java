package com.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un usuario/cliente del sistema
 * 
 * @author Developer
 * @version 1.0.0
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "orders")
@EqualsAndHashCode(exclude = "orders")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
    
    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es obligatorio")
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;
    
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "El teléfono debe tener un formato válido")
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;
    
    @Column(name = "address", length = 500)
    private String address;
    
    @Column(name = "city", length = 100)
    private String city;
    
    @Column(name = "country", length = 100)
    private String country;
    
    @Builder.Default
    @Column(name = "active", nullable = false)
    private Boolean active = true;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Relación One-to-Many con Orders
     * Un usuario puede tener múltiples órdenes
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Order> orders = new ArrayList<>();
} 