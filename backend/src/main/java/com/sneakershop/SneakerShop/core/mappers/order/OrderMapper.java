package com.sneakershop.SneakerShop.core.mappers.order;

import com.sneakershop.SneakerShop.core.models.cart.ShoppingCart;
import com.sneakershop.SneakerShop.core.models.cart.ShoppingCartItem;
import com.sneakershop.SneakerShop.entities.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class OrderMapper {
    public static ToShoppingCart toShoppingCart = new ToShoppingCart();
    public static class ToShoppingCart implements Function<Order, ShoppingCart> {

        @Override
        public ShoppingCart apply(Order order) {
            if (order == null) return null;

            List<ShoppingCartItem> items = new ArrayList<>();
            if (order.getItems() != null) {
                items = order.getItems().stream()
                        .map(OrderItemMapper.toShoppingCartItem)
                        .collect(Collectors.toList());
            }

            return ShoppingCart.builder()
                    .id(order.getId())
                    .client(order.getClient())
                    .items(items)
                    .build();
        }
    }
}
