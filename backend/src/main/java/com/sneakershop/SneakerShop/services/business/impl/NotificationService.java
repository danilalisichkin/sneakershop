package com.sneakershop.SneakerShop.services.business.impl;

import com.sneakershop.SneakerShop.core.dto.request.NotificationDTO;
import com.sneakershop.SneakerShop.core.enums.NotificationStatus;
import com.sneakershop.SneakerShop.core.mappers.notification.NotificationMapper;
import com.sneakershop.SneakerShop.dao.repository.NotificationRepository;
import com.sneakershop.SneakerShop.entities.Notification;
import com.sneakershop.SneakerShop.entities.User;
import com.sneakershop.SneakerShop.exceptions.ResourceNotFoundException;
import com.sneakershop.SneakerShop.services.business.INotificationService;
import com.sneakershop.SneakerShop.services.business.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class NotificationService implements INotificationService {
    private final NotificationRepository notificationRepository;

    private final IUserService userService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, IUserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    @Override
    public void sendNotification(NotificationDTO notificationDTO) {
        User user = userService.getUserByPhone(notificationDTO.getClientPhoneNumber());

        Notification notification = NotificationMapper.toEntity.apply(notificationDTO);
        notification.setUser(user);
        notification.setStatus(NotificationStatus.UNREAD);

        notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Override
    public void readNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                () -> new ResourceNotFoundException("Notification with id=%s not found", notificationId)
        );

        notification.setStatus(NotificationStatus.READ);
        notification.setReadDate(ZonedDateTime.now(ZoneId.of("Europe/Minsk")));

        notificationRepository.save(notification);
    }

    @Override
    public Notification getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Notification with id=%s not found", id)
        );

        return notification;
    }

    @Override
    public List<Notification> getAllUserNotificationsByPhoneNumber(String phoneNumber) {
        List<Notification> notifications = notificationRepository.findByUserPhoneNumber(phoneNumber);

        if (notifications == null || notifications.isEmpty()) {
            throw new ResourceNotFoundException("No notifications found for phone number=%s ", phoneNumber);
        }

        return notifications;
    }
}
