package com.sneakershop.SneakerShop.controllers.api;

import com.sneakershop.SneakerShop.core.dto.request.NotificationDTO;
import com.sneakershop.SneakerShop.services.business.INotificationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationApiController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final INotificationService notificationService;

    @Autowired
    public NotificationApiController(INotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<Object> sendNotification(@Valid @RequestBody NotificationDTO notification) {
        logger.info("Sending notification");

        notificationService.sendNotification(notification);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/read")
    public ResponseEntity<Object> readNotification(@Valid @RequestBody Long NotificationId) {
        logger.info("Reading notification with id=%s", NotificationId);

        notificationService.readNotification(NotificationId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/all/{userPhone}")
    public ResponseEntity<Object> getAllUserNotifications(@PathVariable String userPhone) {
        logger.info("Sending all notifications for user with phone={}", userPhone);

        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getAllUserNotificationsByPhoneNumber(userPhone));
    }
}
