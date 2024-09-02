package com.sneakershop.SneakerShop.core.dto.request;

import com.sneakershop.SneakerShop.core.enums.NotificationType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDTO {

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String content;

    @NotNull
    private NotificationType type;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\+375(15|29|33|44)\\d{7}$",
            message = "illegal format of phone number, correct example: +375291234567")
    private String clientPhoneNumber;
}
