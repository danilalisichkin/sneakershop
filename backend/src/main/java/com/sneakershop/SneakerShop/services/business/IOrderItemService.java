package com.sneakershop.SneakerShop.services.business;

import com.sneakershop.SneakerShop.core.enums.OrderStatus;
import com.sneakershop.SneakerShop.entities.Order;
import com.sneakershop.SneakerShop.entities.OrderItem;
import com.sneakershop.SneakerShop.entities.Product;

import java.util.List;

public interface IOrderItemService {

    OrderItem createNewOrderItem(Order order, Integer quantity, Product product);

    void deleteOrderItemById(Long id);

    OrderItem updateOrderItem(OrderItem orderItem);

    OrderItem getOrderItemById(Long id);

    List<OrderItem> getOrderItemsByOrderId(Long orderId);

    List<OrderItem> getOrderItemsByProductId(Long productId);

    List<OrderItem> getOrderItemsByProductIdAndOrderStatus(Long productId, OrderStatus orderStatus);

    List<OrderItem> saveOrderItems(List<OrderItem> orderItems, Order relatedOrder);

    OrderItem saveOrderItem(OrderItem orderItem, Order relatedOrder);
}
