package com.sneakershop.SneakerShop.core.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PagedProductsInCatalogDTO {
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private Integer totalItems;
    private List<ProductInCatalogDTO> products;
}
