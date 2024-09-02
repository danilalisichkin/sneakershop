package com.sneakershop.SneakerShop.services.business;

import com.sneakershop.SneakerShop.core.dto.request.UserSignInDTO;
import com.sneakershop.SneakerShop.core.dto.request.UserSignUpDTO;
import com.sneakershop.SneakerShop.security.jwt.JwtAuthenticationResponse;

public interface IAuthenticationService {

    JwtAuthenticationResponse signUp(UserSignUpDTO userDTO);

    JwtAuthenticationResponse signIn(UserSignInDTO userDTO);
}
