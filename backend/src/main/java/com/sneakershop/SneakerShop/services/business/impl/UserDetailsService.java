package com.sneakershop.SneakerShop.services.business.impl;

import com.sneakershop.SneakerShop.dao.repository.UserDetailsRepository;
import com.sneakershop.SneakerShop.entities.User;
import com.sneakershop.SneakerShop.entities.UserDetails;
import com.sneakershop.SneakerShop.exceptions.ResourceNotFoundException;
import com.sneakershop.SneakerShop.services.business.IUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class UserDetailsService implements IUserDetailsService {
    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserDetailsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetails createNewUserDetails(User user) {
        UserDetails userDetails = UserDetails.builder()
                .user(user)
                .privacyAgreement(true)
                .totalRedemption(BigDecimal.ZERO)
                .totalRefund(BigDecimal.ZERO)
                .discount(BigDecimal.ZERO)
                .build();

        return userDetailsRepository.save(userDetails);
    }

    @Override
    public UserDetails getUserDetailsById(Long id) {
        return userDetailsRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Details with id=%s not found", id));
    }

    @Override
    public UserDetails getUserDetailsByUser(User user) {
        return userDetailsRepository.findByUser(user).orElseThrow(() ->
                new ResourceNotFoundException("Details for user with phone=%s not found", user.getPhoneNumber()));
    }

    @Override
    public UserDetails setBirthDate(UserDetails userDetails, LocalDate birthDate) {
        userDetails.setBirthDate(birthDate);

        return userDetailsRepository.save(userDetails);
    }

    @Override
    public UserDetails signAdvertisingAgreement(UserDetails userDetails) {
        userDetails.setAdAgreement(true);

        return userDetailsRepository.save(userDetails);
    }

    @Override
    public UserDetails calculateDiscount(UserDetails userDetails) {
        Double earnedDiscount = userDetails.getTotalRedemption().doubleValue() / 1000.00;
        Double deductedDiscount = userDetails.getTotalRefund().doubleValue() / 1500.00;
        Double discount = Math.max(earnedDiscount - deductedDiscount, 15.00);

        userDetails.setDiscount(BigDecimal.valueOf(discount));

        return userDetailsRepository.save(userDetails);
    }

    @Override
    public UserDetails addAmountToTotalRedemption(UserDetails userDetails, BigDecimal amount) {
        userDetails.setTotalRedemption(userDetails.getTotalRedemption().add(amount));

        return  userDetailsRepository.save(userDetails);
    }

    @Override
    public UserDetails addAmountToTotalRefund(UserDetails userDetails, BigDecimal amount) {
        userDetails.setTotalRedemption(userDetails.getTotalRefund().add(amount));

        return  userDetailsRepository.save(userDetails);
    }
}
