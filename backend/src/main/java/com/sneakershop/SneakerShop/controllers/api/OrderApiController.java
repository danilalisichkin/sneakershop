package com.sneakershop.SneakerShop.controllers.api;

import com.sneakershop.SneakerShop.core.dto.request.NotificationDTO;
import com.sneakershop.SneakerShop.core.dto.request.OrderStatusChangingDTO;
import com.sneakershop.SneakerShop.services.business.IOrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderApiController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IOrderService orderService;

    @Autowired
    public OrderApiController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/set-status")
    public ResponseEntity<Object> setOrderStatus(@RequestBody OrderStatusChangingDTO statusChangingDTO) {
        logger.info("Changing status of order with id={}", statusChangingDTO.getOrderId());

        orderService.changeOrderStatus(statusChangingDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
