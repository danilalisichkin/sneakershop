package com.sneakershop.SneakerShop.core.mappers.notification;

import com.sneakershop.SneakerShop.core.dto.request.NotificationDTO;
import com.sneakershop.SneakerShop.entities.Notification;

import java.util.function.Function;

public class NotificationMapper {
    public static ToEntity toEntity = new ToEntity();
    public static ToDTO toDTO = new ToDTO();

    public static class ToEntity implements Function<NotificationDTO, Notification> {

        @Override
        public Notification apply(NotificationDTO notificationDTO) {
            if (notificationDTO == null) return null;

            return Notification.builder()
                    .id(null)
                    .user(null)
                    .sentDate(null)
                    .readDate(null)
                    .status(null)
                    .title(notificationDTO.getTitle())
                    .content(notificationDTO.getContent())
                    .type(notificationDTO.getType())
                    .build();
        }
    }

    public static class ToDTO implements Function<Notification, NotificationDTO> {

        @Override
        public NotificationDTO apply(Notification notification) {
            if (notification == null) return null;

            return NotificationDTO.builder()
                    .title(notification.getTitle())
                    .content(notification.getContent())
                    .type(notification.getType())
                    .build();
        }
    }
}
