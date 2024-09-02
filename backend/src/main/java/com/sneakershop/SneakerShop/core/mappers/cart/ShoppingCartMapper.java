package com.sneakershop.SneakerShop.core.mappers.cart;

import com.sneakershop.SneakerShop.core.enums.OrderStatus;
import com.sneakershop.SneakerShop.core.models.cart.ShoppingCart;
import com.sneakershop.SneakerShop.core.dto.response.ShoppingCartDTO;
import com.sneakershop.SneakerShop.entities.Order;

import java.sql.Timestamp;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ShoppingCartMapper {
    public static ToEntity toEntity = new ToEntity();
    public static ToDTO toDTO = new ToDTO();
    public static ToOrder toOrder = new ToOrder();

    public static class ToEntity implements Function<ShoppingCartDTO, ShoppingCart> {

        @Override
        public ShoppingCart apply(ShoppingCartDTO cartDTO) {
            if (cartDTO == null) return null;

            return ShoppingCart.builder()
                    .id(null)
                    .client(null)
                    .items(StreamSupport
                            .stream(cartDTO.getItems().spliterator(), false)
                            .map(ShoppingCartItemMapper.toEntity).collect(Collectors.toList()))
                    .build();
        }
    }

    public static class ToDTO implements Function<ShoppingCart, ShoppingCartDTO> {

        @Override
        public ShoppingCartDTO apply(ShoppingCart cart) {
            if (cart == null) return null;

            return ShoppingCartDTO.builder()
                    .clientPhoneNumber(cart.getClient().getPhoneNumber())
                    .items(StreamSupport
                            .stream(cart.getItems().spliterator(), false)
                            .map(ShoppingCartItemMapper.toDTO).collect(Collectors.toList()))
                    .build();
        }
    }

    public static class ToOrder implements Function<ShoppingCart, Order> {

        @Override
        public Order apply(ShoppingCart cart) {
            if (cart == null) return null;

            return Order.builder()
                    .id(cart.getId())
                    .client(cart.getClient())
                    .status(OrderStatus.IN_CART)
                    .items(StreamSupport
                            .stream(cart.getItems().spliterator(), false)
                            .map(ShoppingCartItemMapper.toOrderItem).collect(Collectors.toList()))
                    .build();
        }
    }
}
