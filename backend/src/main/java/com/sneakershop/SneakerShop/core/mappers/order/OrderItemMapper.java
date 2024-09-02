package com.sneakershop.SneakerShop.core.mappers.order;

import com.sneakershop.SneakerShop.core.models.cart.ShoppingCartItem;
import com.sneakershop.SneakerShop.entities.OrderItem;

import java.util.function.Function;

public class OrderItemMapper {
    public static ToShoppingCartItem toShoppingCartItem = new ToShoppingCartItem();
    public static class ToShoppingCartItem implements Function<OrderItem, ShoppingCartItem> {

        @Override
        public ShoppingCartItem apply(OrderItem orderItem) {
            if (orderItem == null) return null;

            return ShoppingCartItem.builder()
                    .id(orderItem.getId())
                    .product(orderItem.getProduct())
                    .quantity(orderItem.getQuantity())
                    .build();
        }
    }
}
