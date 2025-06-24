package com.ecommerce.repository;

import com.ecommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad OrderItem
 * 
 * @author Developer
 * @version 1.0.0
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    /**
     * Busca items por orden
     */
    List<OrderItem> findByOrderId(Long orderId);
    
    /**
     * Busca items por producto
     */
    List<OrderItem> findByProductId(Long productId);
    
    /**
     * Busca items por orden y producto
     */
    List<OrderItem> findByOrderIdAndProductId(Long orderId, Long productId);
    
    /**
     * Busca items con descuento
     */
    @Query("SELECT oi FROM OrderItem oi WHERE oi.discountAmount > 0 OR oi.discountPercentage > 0")
    List<OrderItem> findItemsWithDiscount();
    
    /**
     * Calcula la cantidad total vendida por producto
     */
    @Query("SELECT oi.product.id, oi.product.name, SUM(oi.quantity) FROM OrderItem oi " +
           "GROUP BY oi.product.id, oi.product.name ORDER BY SUM(oi.quantity) DESC")
    List<Object[]> findProductSalesQuantity();
    
    /**
     * Calcula el total vendido por producto en dinero
     */
    @Query("SELECT oi.product.id, oi.product.name, SUM(oi.totalPrice) FROM OrderItem oi " +
           "GROUP BY oi.product.id, oi.product.name ORDER BY SUM(oi.totalPrice) DESC")
    List<Object[]> findProductSalesRevenue();
    
    /**
     * Busca los productos más vendidos (top N)
     */
    @Query(value = "SELECT oi.product_id, p.name, SUM(oi.quantity) as total_quantity " +
           "FROM order_items oi JOIN products p ON oi.product_id = p.product_id " +
           "GROUP BY oi.product_id, p.name ORDER BY total_quantity DESC LIMIT :limit", 
           nativeQuery = true)
    List<Object[]> findTopSellingProducts(@Param("limit") int limit);
    
    /**
     * Busca items por rango de cantidad
     */
    List<OrderItem> findByQuantityBetween(Integer minQuantity, Integer maxQuantity);
    
    /**
     * Busca el total de items por usuario (a través de las órdenes)
     */
    @Query("SELECT u.id, u.firstName, u.lastName, COUNT(oi) FROM OrderItem oi " +
           "JOIN oi.order o JOIN o.user u GROUP BY u.id, u.firstName, u.lastName " +
           "ORDER BY COUNT(oi) DESC")
    List<Object[]> findItemCountByUser();
} 