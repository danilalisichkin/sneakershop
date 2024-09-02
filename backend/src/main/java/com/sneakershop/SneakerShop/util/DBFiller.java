package com.sneakershop.SneakerShop.util;

import com.sneakershop.SneakerShop.core.enums.Gender;
import com.sneakershop.SneakerShop.core.enums.Season;
import com.sneakershop.SneakerShop.core.enums.ShoeType;
import com.sneakershop.SneakerShop.dao.repository.ProductRepository;
import com.sneakershop.SneakerShop.dao.repository.ShoeDetailsRepository;
import com.sneakershop.SneakerShop.dao.repository.ShoeModelRepository;
import com.sneakershop.SneakerShop.entities.Product;
import com.sneakershop.SneakerShop.entities.ShoeDetails;
import com.sneakershop.SneakerShop.entities.ShoeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class DBFiller {
    private final ProductRepository productRepository;
    private final  ShoeModelRepository shoeModelRepository;
    private final  ShoeDetailsRepository shoeDetailsRepository;

    @Autowired
    public DBFiller(ProductRepository productRepository, ShoeModelRepository shoeModelRepository, ShoeDetailsRepository shoeDetailsRepository) {
        this.productRepository = productRepository;
        this.shoeModelRepository = shoeModelRepository;
        this.shoeDetailsRepository = shoeDetailsRepository;
    }

    public void fillShoeModelTable() {

        ShoeModel shoeModel = ShoeModel.builder()
                .brandName("New Balance")
                .modelName("530 Pink-Purple")
                .imageUrl("https://cdn.sneakerbaron.nl/uploads/2020/06/23145056/new-balance-530-silver-metallickite-purple-700x700.png")
                .description("Nothing as fly, nothing as comfortable, nothing as proven.")
                .build();
        shoeModel = shoeModelRepository.save(shoeModel);

        ShoeDetails shoeDetails = ShoeDetails.builder()
                .shoeModel(shoeModel)
                .shoeType(ShoeType.SNEAKERS)
                .gender(Gender.FEMALE)
                .season(Season.SUMMER)
                .color("Purple, Pink, White")
                .material("Synthetic")
                .style("Sport")
                .imagesUrls(List.of(
                        shoeModel.getImageUrl(),
                        shoeModel.getImageUrl(),
                        shoeModel.getImageUrl(),
                        shoeModel.getImageUrl(),
                        shoeModel.getImageUrl())
                )
                .build();
        shoeDetailsRepository.save(shoeDetails);

        Product product = Product.builder()
                .shoeModel(shoeModel)
                .size(BigDecimal.valueOf(45.0))
                .price(BigDecimal.valueOf(119.99))
                .quantity(10)
                .build();
        productRepository.save(product);
        product = Product.builder()
                .size(BigDecimal.valueOf(38))
                .price(BigDecimal.valueOf(194.99))
                .quantity(3)
                .shoeModel(shoeModel)
                .build();
        productRepository.save(product);

        // PRODUCT

        shoeModel = ShoeModel.builder()
                .brandName("New Balance")
                .modelName("530 Silver")
                .imageUrl("https://nbalance.by/wp-content/uploads/2020/12/mr530sg_nb_02_i.jpg")
                .description("Nothing as fly, nothing as comfortable, nothing as proven.")
                .build();
        shoeModel = shoeModelRepository.save(shoeModel);

        shoeDetails = ShoeDetails.builder()
                .shoeModel(shoeModel)
                .shoeType(ShoeType.SNEAKERS)
                .gender(Gender.UNISEX)
                .season(Season.SUMMER)
                .color("Silver, White")
                .material("Synthetic")
                .style("Sport")
                .imagesUrls(List.of(
                        shoeModel.getImageUrl(),
                        shoeModel.getImageUrl(),
                        shoeModel.getImageUrl(),
                        shoeModel.getImageUrl(),
                        shoeModel.getImageUrl())
                )
                .build();
        shoeDetailsRepository.save(shoeDetails);

        product = Product.builder()
                .shoeModel(shoeModel)
                .size(BigDecimal.valueOf(43.0))
                .price(BigDecimal.valueOf(119.99))
                .quantity(10)
                .build();
        productRepository.save(product);
        product = Product.builder()
                .size(BigDecimal.valueOf(39))
                .price(BigDecimal.valueOf(194.99))
                .quantity(3)
                .shoeModel(shoeModel)
                .build();
        productRepository.save(product);

        // PRODUCT
    }

}
