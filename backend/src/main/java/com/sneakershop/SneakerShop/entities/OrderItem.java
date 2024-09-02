package com.sneakershop.SneakerShop.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "relevant_price", nullable = false)
    private BigDecimal relevantPrice;
}
