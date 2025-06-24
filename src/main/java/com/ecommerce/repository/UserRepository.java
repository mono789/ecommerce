package com.ecommerce.repository;

import com.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad User
 * 
 * @author Developer
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Busca un usuario por email
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Verifica si existe un usuario con el email dado
     */
    boolean existsByEmail(String email);
    
    /**
     * Busca usuarios por estado activo
     */
    Page<User> findByActive(Boolean active, Pageable pageable);
    
    /**
     * Busca usuarios por nombre y apellido (case insensitive)
     */
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')) AND " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))")
    List<User> findByFirstNameAndLastNameContainingIgnoreCase(
            @Param("firstName") String firstName, 
            @Param("lastName") String lastName);
    
    /**
     * Busca usuarios por ciudad
     */
    List<User> findByCityIgnoreCaseAndActiveTrue(String city);
    
    /**
     * Busca usuarios por país
     */
    List<User> findByCountryIgnoreCaseAndActiveTrue(String country);
    
    /**
     * Busca usuarios con órdenes
     */
    @Query("SELECT DISTINCT u FROM User u JOIN u.orders o WHERE u.active = true")
    List<User> findUsersWithOrders();
} 