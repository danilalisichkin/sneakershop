package com.sneakershop.SneakerShop.core.dto.response;

import com.sneakershop.SneakerShop.core.enums.Gender;
import com.sneakershop.SneakerShop.core.enums.Season;
import com.sneakershop.SneakerShop.core.enums.ShoeType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShoeModelInCardDTO {
    private Long id;
    private String brandName;
    private String modelName;
    private String imageUrl;
    private ShoeType shoeType;
    private Gender gender;
    private Season season;
    private String color;
    private String material;
    private String style;
    private String description;
}
