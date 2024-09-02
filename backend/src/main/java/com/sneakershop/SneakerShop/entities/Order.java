package com.sneakershop.SneakerShop.entities;

import com.sneakershop.SneakerShop.core.enums.OrderStatus;
import com.sneakershop.SneakerShop.core.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Column(name = "complete_date")
    private ZonedDateTime completeDate;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;
}
