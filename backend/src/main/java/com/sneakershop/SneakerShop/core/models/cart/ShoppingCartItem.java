package com.sneakershop.SneakerShop.core.models.cart;

import com.sneakershop.SneakerShop.entities.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShoppingCartItem {
    private Long id;
    private Product product;
    private Integer quantity;
}
