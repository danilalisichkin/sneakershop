package com.sneakershop.SneakerShop.core.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignInDTO {

    @NotNull
    @NotBlank
    private String identifier;

    @NotNull
    @NotBlank
    private String password;
}
