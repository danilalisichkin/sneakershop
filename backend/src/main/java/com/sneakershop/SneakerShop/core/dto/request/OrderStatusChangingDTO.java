package com.sneakershop.SneakerShop.core.dto.request;

import com.sneakershop.SneakerShop.core.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderStatusChangingDTO {

    @NotNull
    private Long orderId;

    @NotNull
    @NotBlank
    private OrderStatus orderStatus;
}