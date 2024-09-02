package com.sneakershop.SneakerShop.dao.repository;

import com.sneakershop.SneakerShop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.shoeModel.id = ?1")
    List<Product> findByModelId(Long shoeModelId);

    @Query("SELECT p.size FROM Product p WHERE p.shoeModel.id = ?1 AND p.quantity > 0")
    List<Integer> findAvailableSizesByShoeModelId(Long shoeModelId);

    @Query("SELECT MIN(p.price) FROM Product p WHERE p.shoeModel.id = ?1")
    Double findMinPriceByShoeModelId(Long shoeModelId);

    @Query("SELECT p FROM Product p WHERE p.shoeModel.id = ?1 AND p.size = ?2")
    Product findByModelIdAndSize(Long shoeModelId, Double size);

    @Query("SELECT p FROM Product p " +
            "WHERE (:searchString IS NULL OR LOWER(p.shoeModel.modelName) LIKE LOWER(CONCAT('%', :searchString, '%')) " +
            "OR LOWER(p.shoeModel.brandName) LIKE LOWER(CONCAT('%', :searchString, '%'))) " +
            "AND p.id IN (SELECT MIN(p2.id) FROM Product p2 GROUP BY p2.shoeModel)")
    Page<Product> findDistinctProductsByShoeModelWithSearch(@Param("searchString") String searchString, Pageable pageable);
}
