package com.sneakershop.SneakerShop.core.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductInCatalogDTO {
    private Long shoeModelId;
    private String imageUrl;
    private String brandName;
    private String modelName;
    private Double minPrice;
    private List<Integer> sizes;
}
