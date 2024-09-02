package com.sneakershop.SneakerShop.core.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductInCartDTO {
    private Long productId;
    private Long shoeModelId;
    private String brandName;
    private String modelName;
    private String imageUrl;
    private Double size;
    private Double price;
    private Integer quantity;
}
