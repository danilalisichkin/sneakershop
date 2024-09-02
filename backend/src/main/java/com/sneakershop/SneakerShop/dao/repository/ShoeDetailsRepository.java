package com.sneakershop.SneakerShop.dao.repository;

import com.sneakershop.SneakerShop.entities.ShoeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoeDetailsRepository extends JpaRepository<ShoeDetails, Long> {
    @Query("SELECT d FROM ShoeDetails d WHERE d.shoeModel = ?1")
    ShoeDetails findByShoeModelId(Long id);
}
