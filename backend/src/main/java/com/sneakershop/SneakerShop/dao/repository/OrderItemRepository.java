package com.sneakershop.SneakerShop.dao.repository;

import com.sneakershop.SneakerShop.core.enums.OrderStatus;
import com.sneakershop.SneakerShop.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi FROM OrderItem oi WHERE oi.id = ?1")
    List<OrderItem> findByOrderId(Long id);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.product.id = ?1")
    List<OrderItem> findByProductId(Long id);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.product.id = ?1 AND oi.order.status = ?2")
    List<OrderItem> findByProductIdAndOrderStatus(Long productId, OrderStatus orderStatus);
}
