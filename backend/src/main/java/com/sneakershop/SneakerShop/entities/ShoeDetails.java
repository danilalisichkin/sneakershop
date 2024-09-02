package com.sneakershop.SneakerShop.entities;

import com.sneakershop.SneakerShop.core.enums.Gender;
import com.sneakershop.SneakerShop.core.enums.Season;
import com.sneakershop.SneakerShop.core.enums.ShoeType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shoe_details")
public class ShoeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "model_id")
    private ShoeModel shoeModel;

    @Enumerated(EnumType.STRING)
    @Column(name = "shoe_type", nullable = false)
    private ShoeType shoeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "season", nullable = false)
    private Season season;

    @Column(name = "color")
    private String color;

    @Column(name = "material")
    private String material;

    @Column(name = "style")
    private String style;

    @Column(name = "images_urls")
    @CollectionTable(name = "shoe_images", joinColumns = @JoinColumn(name = "details_id"))
    @ElementCollection
    private List<String> imagesUrls;
}
