package com.sneakershop.SneakerShop.services.business.impl;

import com.sneakershop.SneakerShop.core.dto.request.OrderStatusChangingDTO;
import com.sneakershop.SneakerShop.core.enums.OrderStatus;
import com.sneakershop.SneakerShop.dao.repository.OrderRepository;
import com.sneakershop.SneakerShop.entities.Order;
import com.sneakershop.SneakerShop.entities.User;
import com.sneakershop.SneakerShop.services.business.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderService implements IOrderService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createNewOrder(User user) {
        Order order = new Order();

        order.setClient(user);
        order.setStatus(OrderStatus.PENDING);

        return order;
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long id) {
        orderRepository.updateOrderStatus(id, OrderStatus.CANCELLED);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public List<Order> getClientOrders(User user) {
        return orderRepository.findByClientPhoneNumber(user.getPhoneNumber());
    }

    @Override
    public Order getLastClientOrder(User user) {
        return orderRepository.findLastOrderByClientPhoneNumber(user.getPhoneNumber());
    }

    @Override
    public List<Order> getClientOrdersByStatus(User user, OrderStatus status) {
        return orderRepository.findByClientPhoneNumberAndStatus(user.getPhoneNumber(), status);
    }

    @Override
    public List<Order> getAllOrdersByStatus(OrderStatus status) {
        return orderRepository.findByOrderStatus(status);
    }

    @Override
    public Order setOrderStatus(Order order, OrderStatus status) {
        order.setStatus(status);

        return orderRepository.save(order);
    }

    @Override
    public Order changeOrderStatus(OrderStatusChangingDTO changingDTO) {
        Order order = getOrderById(changingDTO.getOrderId());

        return setOrderStatus(order, changingDTO.getOrderStatus());
    }
}
