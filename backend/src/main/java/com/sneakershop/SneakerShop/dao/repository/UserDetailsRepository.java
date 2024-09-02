package com.sneakershop.SneakerShop.dao.repository;

import com.sneakershop.SneakerShop.entities.User;
import com.sneakershop.SneakerShop.entities.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    @Query("SELECT d FROM UserDetails d WHERE d.user= ?1")
    Optional<UserDetails> findByUser(User user);
}
