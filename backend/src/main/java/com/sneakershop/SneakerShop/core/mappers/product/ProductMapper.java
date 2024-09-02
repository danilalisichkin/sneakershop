package com.sneakershop.SneakerShop.core.mappers.product;

import com.sneakershop.SneakerShop.core.dto.response.ProductInCardDTO;
import com.sneakershop.SneakerShop.core.dto.response.ProductInCartDTO;
import com.sneakershop.SneakerShop.core.dto.response.ProductInCatalogDTO;
import com.sneakershop.SneakerShop.entities.Product;

import java.util.function.Function;

public class ProductMapper {
    public static ToInCatalogDTO toInCatalogDTO = new ToInCatalogDTO();
    public static ToInCardDTO toInCardDTO = new ToInCardDTO();
    public static ToInCartDTO toInCartDTO = new ToInCartDTO();

    public static class ToInCatalogDTO implements Function<Product, ProductInCatalogDTO> {

        @Override
        public ProductInCatalogDTO apply(Product product) {
            if (product == null) return null;

            return ProductInCatalogDTO.builder()
                    .shoeModelId(product.getShoeModel().getId())
                    .brandName(product.getShoeModel().getBrandName())
                    .modelName(product.getShoeModel().getModelName())
                    .imageUrl(product.getShoeModel().getImageUrl())
                    .minPrice(null)
                    .sizes(null)
                    .build();
        }
    }

    public static class ToInCardDTO implements  Function <Product, ProductInCardDTO> {
        @Override
        public ProductInCardDTO apply(Product product) {
            if (product == null) return null;

            return ProductInCardDTO.builder()
                    .productId(product.getId())
                    .price(product.getPrice().doubleValue())
                    .size(product.getSize().doubleValue())
                    .build();
        }
    }

    public static class ToInCartDTO implements  Function <Product, ProductInCartDTO> {
        @Override
        public ProductInCartDTO apply(Product product) {
            if (product == null) return null;

            return ProductInCartDTO.builder()
                    .productId(product.getId())
                    .shoeModelId(product.getShoeModel().getId())
                    .brandName(product.getShoeModel().getBrandName())
                    .modelName(product.getShoeModel().getModelName())
                    .imageUrl(product.getShoeModel().getImageUrl())
                    .price(product.getPrice().doubleValue())
                    .size(product.getSize().doubleValue())
                    .quantity(null)
                    .build();
        }
    }
}
