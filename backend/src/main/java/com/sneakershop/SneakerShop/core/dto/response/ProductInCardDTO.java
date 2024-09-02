package com.sneakershop.SneakerShop.core.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductInCardDTO {
    private Long productId;
    private Double size;
    private Double price;
}
