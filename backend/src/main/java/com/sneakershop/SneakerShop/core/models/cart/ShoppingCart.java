package com.sneakershop.SneakerShop.core.models.cart;

import com.sneakershop.SneakerShop.entities.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShoppingCart {
    private Long id;
    private List<ShoppingCartItem> items;
    private User client;
}
