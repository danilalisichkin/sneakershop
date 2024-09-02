package com.sneakershop.SneakerShop.services.business;

import com.sneakershop.SneakerShop.core.dto.request.OrderStatusChangingDTO;
import com.sneakershop.SneakerShop.core.enums.OrderStatus;
import com.sneakershop.SneakerShop.entities.Order;
import com.sneakershop.SneakerShop.entities.User;

import java.util.List;

public interface IOrderService {

    Order createNewOrder(User user);

    Order saveOrder(Order order);

    void cancelOrder(Long id);

    Order getOrderById(Long id);

    List<Order> getClientOrders(User user);

    Order getLastClientOrder(User user);

    List<Order> getClientOrdersByStatus(User user, OrderStatus status);

    List<Order> getAllOrdersByStatus(OrderStatus status);

    Order setOrderStatus(Order order, OrderStatus status);

    Order changeOrderStatus(OrderStatusChangingDTO changingDTO);
}
