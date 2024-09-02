package com.sneakershop.SneakerShop.controllers.api;

import com.sneakershop.SneakerShop.core.dto.response.PagedProductsInCatalogDTO;
import com.sneakershop.SneakerShop.core.dto.response.ProductInCardDTO;
import com.sneakershop.SneakerShop.core.dto.response.ProductInCatalogDTO;
import com.sneakershop.SneakerShop.exceptions.BadRequestException;
import com.sneakershop.SneakerShop.services.business.IProductService;
import com.sneakershop.SneakerShop.util.DBFiller;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductApiController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IProductService productService;

    private final DBFiller dbFiller;

    @Autowired
    public ProductApiController(IProductService productService, DBFiller dbFiller) {
        this.productService = productService;
        this.dbFiller = dbFiller;
    }

    @GetMapping("/catalog")
    public ResponseEntity<Object> getProductsInCatalog(
            @Min(value = 1) @RequestParam(required = false, name = "page", defaultValue = "1") Integer pageNumber,
            @Min(value = 1) @RequestParam(required = false, name = "size", defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, name = "sort", defaultValue = "id") String sortField,
            @RequestParam(required = false, name = "order", defaultValue = "desc") String sortOrder,
            @RequestParam(required = false, name = "search", defaultValue = "") String searchString
    ) {
        // dbFiller.fillShoeModelTable();
        if (!sortOrder.equalsIgnoreCase("asc") && !sortOrder.equalsIgnoreCase("desc")) {
            throw new BadRequestException("Sort order should be asc or desc");
        }

        Sort.Direction sortDirection = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest request = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortDirection, sortField));
        logger.info("Sending page of product catalog with request: {}", request);

        PagedProductsInCatalogDTO page = productService.getPagedProductsInCatalog(request, searchString);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/catalog/{id}")
    public ResponseEntity<Object> getProductInCatalogById(@PathVariable Long id) {
        logger.info("Sending product with id={}", id);

        ProductInCatalogDTO productInCatalog = productService.getProductInCatalogById(id);

        return ResponseEntity.status(HttpStatus.OK).body(productInCatalog);
    }

    @GetMapping("/catalog/all")
    public ResponseEntity<Object> getAllProducts() {
        logger.info("Sending all products");

        List<ProductInCatalogDTO> products = productService.getAllProductsInCatalog();

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/details/price-quantity")
    public ResponseEntity<Object> getProductQuantityAndPriceByModelAndSize(
            @RequestParam (name = "model-id") Long shoeModelId,
            @RequestParam (name = "shoe-size") Double size) {
        logger.info("Sending information about product with shoe model id={} and size={}", shoeModelId, size);

        ProductInCardDTO productInCardDTO = productService.getProductInCardByModelIdAndSize(shoeModelId, size);

        if (productInCardDTO == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found or out of stock");
        return ResponseEntity.status(HttpStatus.OK).body(productInCardDTO);
    }

    @PutMapping("/update-newness")
    public ResponseEntity<Object> updateNewness(@RequestBody ProductInCardDTO productInCardDTO) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
