package com.sneakershop.SneakerShop.dao.repository;

import com.sneakershop.SneakerShop.core.enums.OrderStatus;
import com.sneakershop.SneakerShop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.client.phoneNumber = ?1")
    List<Order> findByClientPhoneNumber(String phoneNumber);

    @Query("SELECT o FROM Order o WHERE o.client.phoneNumber = ?1 ORDER BY o.creationDate DESC LIMIT 1")
    Order findLastOrderByClientPhoneNumber(String phoneNumber);

    @Query("SELECT o FROM Order o WHERE o.status = ?1")
    List<Order> findByOrderStatus(OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.creationDate = ?1")
    List<Order> findByCreationDate(ZonedDateTime date);

    @Query("SELECT o FROM Order o WHERE o.creationDate > ?1")
    List<Order> findByCreatedAfterDate(ZonedDateTime date);

    @Query("SELECT o FROM Order o WHERE o.client.phoneNumber = ?1 AND o.status = ?2")
    List<Order> findByClientPhoneNumberAndStatus(String phoneNumber, OrderStatus status);

    @Modifying
    // @Query("UPDATE Order o SET o.status = ?1 WHERE o.id = ?2")
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
    void updateOrderStatus(@Param("orderId") Long orderId, @Param("status") OrderStatus status);
}

