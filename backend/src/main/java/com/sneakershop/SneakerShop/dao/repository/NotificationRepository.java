package com.sneakershop.SneakerShop.dao.repository;

import com.sneakershop.SneakerShop.core.enums.NotificationStatus;
import com.sneakershop.SneakerShop.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.user.phoneNumber = ?1 ORDER BY n.sentDate DESC")
    List<Notification> findByUserPhoneNumber(String phoneNumber);

    @Query("SELECT n from Notification n WHERE n.status = ?1 ORDER BY n.sentDate DESC")
    List<Notification> findByNotificationStatus(NotificationStatus notificationStatus);
}
