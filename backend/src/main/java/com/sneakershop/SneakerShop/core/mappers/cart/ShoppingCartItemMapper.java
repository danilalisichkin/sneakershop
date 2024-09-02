package com.sneakershop.SneakerShop.core.mappers.cart;

import com.sneakershop.SneakerShop.core.models.cart.ShoppingCartItem;
import com.sneakershop.SneakerShop.core.dto.response.ShoppingCartItemDTO;
import com.sneakershop.SneakerShop.entities.OrderItem;

import java.util.function.Function;

public class ShoppingCartItemMapper {
    public static ToEntity toEntity = new ToEntity();
    public static ToDTO toDTO = new ToDTO();
    public static ToOrderItem toOrderItem = new ToOrderItem();

    public static class ToEntity implements Function<ShoppingCartItemDTO, ShoppingCartItem> {

        @Override
        public ShoppingCartItem apply(ShoppingCartItemDTO cartItemDTO) {
            if (cartItemDTO == null) return null;

            return ShoppingCartItem.builder()
                    .id(cartItemDTO.getItemId())
                    .product(null)
                    .quantity(null)
                    .build();
        }
    }

    public static class ToDTO implements Function<ShoppingCartItem, ShoppingCartItemDTO> {

        @Override
        public ShoppingCartItemDTO apply(ShoppingCartItem cartItem) {
            if (cartItem == null) return null;

            return ShoppingCartItemDTO.builder()
                    .itemId(cartItem.getId())
                    .build();
        }
    }

    public static class ToOrderItem implements Function<ShoppingCartItem, OrderItem> {

        @Override
        public OrderItem apply(ShoppingCartItem cartItem) {
            if (cartItem == null) return null;

            return OrderItem.builder()
                    .id(cartItem.getId())
                    .product(cartItem.getProduct())
                    .order(null)
                    .quantity(cartItem.getQuantity())
                    .relevantPrice(cartItem.getProduct().getPrice())
                    .build();
        }
    }
}
