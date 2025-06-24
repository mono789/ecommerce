package com.ecommerce.repository;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.Order.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Order
 * 
 * @author Developer
 * @version 1.0.0
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    /**
     * Busca una orden por número de orden
     */
    Optional<Order> findByOrderNumber(String orderNumber);
    
    /**
     * Verifica si existe una orden con el número dado
     */
    boolean existsByOrderNumber(String orderNumber);
    
    /**
     * Busca órdenes por usuario
     */
    Page<Order> findByUserId(Long userId, Pageable pageable);
    
    /**
     * Busca órdenes por estado
     */
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    
    /**
     * Busca órdenes por usuario y estado
     */
    List<Order> findByUserIdAndStatus(Long userId, OrderStatus status);
    
    /**
     * Busca órdenes por rango de fechas
     */
    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate ORDER BY o.createdAt DESC")
    List<Order> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                               @Param("endDate") LocalDateTime endDate);
    
    /**
     * Busca órdenes por rango de total
     */
    List<Order> findByTotalBetweenOrderByTotalDesc(BigDecimal minTotal, BigDecimal maxTotal);
    
    /**
     * Busca órdenes por email del usuario
     */
    @Query("SELECT o FROM Order o JOIN o.user u WHERE LOWER(u.email) = LOWER(:email) ORDER BY o.createdAt DESC")
    List<Order> findByUserEmail(@Param("email") String email);
    
    /**
     * Busca las últimas órdenes de un usuario
     */
    List<Order> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);
    
    /**
     * Calcula el total de ventas por estado
     */
    @Query("SELECT o.status, SUM(o.total), COUNT(o) FROM Order o GROUP BY o.status")
    List<Object[]> findSalesSummaryByStatus();
    
    /**
     * Busca órdenes pendientes de más de X días
     */
    @Query("SELECT o FROM Order o WHERE o.status = 'PENDING' AND o.createdAt < :cutoffDate")
    List<Order> findPendingOrdersOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);
} 