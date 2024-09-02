package com.sneakershop.SneakerShop.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shoe_models")
public class ShoeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @Column(name = "model_name", nullable = false)
    private String modelName;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToOne
    @JoinColumn(name = "shoe_id")
    private ShoeDetails shoeDetails;

    @Column(name = "description", length = 2048)
    private String description;
}
