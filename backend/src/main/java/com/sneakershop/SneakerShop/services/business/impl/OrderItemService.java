package com.sneakershop.SneakerShop.services.business.impl;

import com.sneakershop.SneakerShop.core.enums.OrderStatus;
import com.sneakershop.SneakerShop.entities.Order;
import com.sneakershop.SneakerShop.entities.OrderItem;
import com.sneakershop.SneakerShop.entities.Product;
import com.sneakershop.SneakerShop.dao.repository.OrderItemRepository;
import com.sneakershop.SneakerShop.exceptions.ResourceNotFoundException;
import com.sneakershop.SneakerShop.services.business.IOrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService implements IOrderItemService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem createNewOrderItem(Order order, Integer quantity, Product product) {
        OrderItem orderItem = new OrderItem();

        orderItem.setOrder(order);
        orderItem.setQuantity(quantity);
        orderItem.setProduct(product);

        return orderItem;
    }

    @Override
    public void deleteOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Order item not found"));
        orderItemRepository.delete(orderItem);
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem getOrderItemById(Long id) { return orderItemRepository.getById(id); }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public List<OrderItem> getOrderItemsByProductIdAndOrderStatus(Long productId, OrderStatus orderStatus) {
        return orderItemRepository.findByProductIdAndOrderStatus(productId, orderStatus);
    }

    @Override
    public List<OrderItem> getOrderItemsByProductId(Long productId) {
        return orderItemRepository.findByProductId(productId);
    }

    @Override
    public List<OrderItem> saveOrderItems(List<OrderItem> orderItems, Order relatedOrder)
    {
        List<OrderItem> orderItemList = new ArrayList();

        for (OrderItem item: orderItems) {
            item.setOrder(relatedOrder);
            orderItemList.add(orderItemRepository.save(item));
        }

        return orderItemList;
    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem, Order relatedOrder)
    {
        orderItem.setOrder(relatedOrder);
        return orderItemRepository.save(orderItem);
    }
}
