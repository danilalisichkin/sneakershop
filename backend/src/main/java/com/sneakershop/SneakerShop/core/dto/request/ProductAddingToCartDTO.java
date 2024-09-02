package com.sneakershop.SneakerShop.core.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductAddingToCartDTO {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\+375(15|29|33|44)\\d{7}$",
            message = "illegal format of phone number, correct example: +375291234567")
    private String clientPhoneNumber;

    @NotNull
    private Long productId;

    @NotNull
    @Positive
    @Min(value = 1)
    @Max(value = 20)
    private Integer quantity;
}
