package com.sneakershop.SneakerShop.core.dto.request;

import com.sneakershop.SneakerShop.core.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartPlacingDTO {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\+375(15|29|33|44)\\d{7}$",
            message = "illegal format of phone number, correct example: +375291234567")
    private String clientPhoneNumber;

    @NotNull
    @NotBlank
    private String deliveryAddress;

    @NotNull
    @NotBlank
    private PaymentMethod paymentMethod;
}
