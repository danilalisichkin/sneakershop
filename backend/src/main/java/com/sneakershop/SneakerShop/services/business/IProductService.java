package com.sneakershop.SneakerShop.services.business;

import com.sneakershop.SneakerShop.core.dto.response.PagedProductsInCatalogDTO;
import com.sneakershop.SneakerShop.core.dto.response.ProductInCardDTO;
import com.sneakershop.SneakerShop.core.dto.response.ProductInCatalogDTO;
import com.sneakershop.SneakerShop.entities.Product;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IProductService {

    Product getProductById(Long id);

    List<Product> getProductsByIds(List<Long> ids);

    Product getProductByModelIdAndSize(Long modelId, Double size);

    ProductInCatalogDTO getProductInCatalogById(Long id);

    List<ProductInCatalogDTO> getAllProductsInCatalog();

    PagedProductsInCatalogDTO getPagedProductsInCatalog(PageRequest pageRequest, String searchString);

    ProductInCardDTO getProductInCardByModelIdAndSize(Long modelId, Double size);

    List<ProductInCardDTO> getProductsInCardByModelId(Long modelId);

    Product setProductQuantity(Product product, Integer quantity);

    Product setProductPrice(Product product, BigDecimal price);

    Product updateProductNewness(Product product);
}
