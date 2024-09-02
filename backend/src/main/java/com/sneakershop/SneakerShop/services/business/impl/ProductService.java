package com.sneakershop.SneakerShop.services.business.impl;

import com.google.common.collect.Lists;
import com.sneakershop.SneakerShop.core.dto.response.PagedProductsInCatalogDTO;
import com.sneakershop.SneakerShop.core.dto.response.ProductInCardDTO;
import com.sneakershop.SneakerShop.core.dto.response.ProductInCatalogDTO;
import com.sneakershop.SneakerShop.core.mappers.product.ProductMapper;
import com.sneakershop.SneakerShop.dao.repository.ProductRepository;
import com.sneakershop.SneakerShop.entities.Product;
import com.sneakershop.SneakerShop.exceptions.ResourceNotFoundException;
import com.sneakershop.SneakerShop.services.business.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService implements IProductService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("product with id=%s not found", id)
        );
    }

    @Override
    public List<Product> getProductsByIds(List<Long> ids) {
        List<Product> products = new ArrayList<>();

        for (Long id : ids) {
            products.add(getProductById(id));
        }

        return products;
    }

    @Override
    public Product getProductByModelIdAndSize(Long modelId, Double size) {
        return productRepository.findByModelIdAndSize(modelId, size);
    }

    @Override
    public ProductInCatalogDTO getProductInCatalogById(Long id) {
        ProductInCatalogDTO productInCatalog = ProductMapper.toInCatalogDTO.apply(getProductById(id));
        productInCatalog.setSizes(productRepository.findAvailableSizesByShoeModelId(productInCatalog.getShoeModelId()));
        productInCatalog.setMinPrice(productRepository.findMinPriceByShoeModelId(productInCatalog.getShoeModelId()));

        return productInCatalog;
    }

    @Override
    public List<ProductInCatalogDTO> getAllProductsInCatalog() {
        List<Product> products = Lists.newArrayList(productRepository.findAll());

        List<ProductInCatalogDTO> productsInCatalog = StreamSupport
                .stream(products.spliterator(), false)
                .map(ProductMapper.toInCatalogDTO).collect(Collectors.toList());

        for (var item : productsInCatalog) {
            item.setSizes(productRepository.findAvailableSizesByShoeModelId(item.getShoeModelId()));
            item.setMinPrice(productRepository.findMinPriceByShoeModelId(item.getShoeModelId()));
        }

        return productsInCatalog;
    }

    @Override
    public PagedProductsInCatalogDTO getPagedProductsInCatalog(PageRequest pageRequest, String searchString) {
        Page<Product> page = productRepository.findDistinctProductsByShoeModelWithSearch(searchString, pageRequest);
        if (page.isEmpty()) {
            throw new ResourceNotFoundException("Page not found");
        }

        PagedProductsInCatalogDTO pageDto = PagedProductsInCatalogDTO.builder()
                .page(page.getNumber() + 1)
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .products(StreamSupport
                        .stream(page.getContent().spliterator(), false)
                        .map(ProductMapper.toInCatalogDTO).collect(Collectors.toList()))
                .totalItems(page.getContent().size())
                .build();

        for (var item : pageDto.getProducts()) {
            item.setSizes(productRepository.findAvailableSizesByShoeModelId(item.getShoeModelId()));
            item.setMinPrice(productRepository.findMinPriceByShoeModelId(item.getShoeModelId()));
        }

        return pageDto;
    }

    @Override
    public ProductInCardDTO getProductInCardByModelIdAndSize(Long modelId, Double size) {
        return ProductMapper.toInCardDTO.apply(getProductByModelIdAndSize(modelId, size));
    }

    @Override
    public List<ProductInCardDTO> getProductsInCardByModelId(Long modelId) {
        List<Product> products = productRepository.findByModelId(modelId);

        return StreamSupport.stream(products.spliterator(), false)
                .map(ProductMapper.toInCardDTO).collect(Collectors.toList());
    }

    @Override
    public Product setProductQuantity(Product product, Integer quantity) {
        product.setQuantity(quantity);

        return productRepository.save(product);
    }

    @Override
    public Product setProductPrice(Product product, BigDecimal price) {
        product.setPrice(price);

        return productRepository.save(product);
    }

    @Override
    public Product updateProductNewness(Product product) {
        product.setNewnessDate(LocalDateTime.now());

        return productRepository.save(product);
    }
}
