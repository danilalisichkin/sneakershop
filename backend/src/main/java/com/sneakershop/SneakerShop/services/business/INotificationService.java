package com.sneakershop.SneakerShop.services.business;

import com.sneakershop.SneakerShop.core.dto.request.NotificationDTO;
import com.sneakershop.SneakerShop.entities.Notification;

import java.util.List;

public interface INotificationService {

    void sendNotification(NotificationDTO notification);

    void deleteNotification(Long notificationId);

    void readNotification(Long notificationId);

    Notification getNotificationById(Long id);

    List<Notification> getAllUserNotificationsByPhoneNumber(String phoneNumber);
}
