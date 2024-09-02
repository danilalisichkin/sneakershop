package com.sneakershop.SneakerShop.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private ShoeModel shoeModel;

    @Column(name = "newness")
    @CreatedDate
    private LocalDateTime newnessDate;

    @Column(name = "size", precision = 3, scale = 1)
    private BigDecimal size;

    @Column(name = "price", precision = 6, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;
}
