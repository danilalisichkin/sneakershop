package com.sneakershop.SneakerShop.core.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShoppingCartDTO {
    private String clientPhoneNumber;
    private List<ShoppingCartItemDTO> items;
}
