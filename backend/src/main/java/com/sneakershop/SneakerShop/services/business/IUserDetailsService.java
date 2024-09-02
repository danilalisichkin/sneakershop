package com.sneakershop.SneakerShop.services.business;

import com.sneakershop.SneakerShop.entities.User;
import com.sneakershop.SneakerShop.entities.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IUserDetailsService {

    UserDetails createNewUserDetails(User user);

    UserDetails getUserDetailsById(Long id);

    UserDetails getUserDetailsByUser(User user);

    UserDetails setBirthDate(UserDetails userDetails, LocalDate birthDate);

    UserDetails signAdvertisingAgreement(UserDetails userDetails);

    UserDetails calculateDiscount(UserDetails userDetails);

    UserDetails addAmountToTotalRedemption(UserDetails userDetails, BigDecimal amount);

    UserDetails addAmountToTotalRefund(UserDetails userDetails, BigDecimal amount);
}
