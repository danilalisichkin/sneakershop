package com.sneakershop.SneakerShop.dao.repository;

import com.sneakershop.SneakerShop.entities.ShoeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShoeModelRepository extends JpaRepository<ShoeModel, Long> {
    @Query("SELECT m FROM ShoeModel m WHERE m.modelName = ?1")
    List<ShoeModel> findByModelName(String modelName);
}
