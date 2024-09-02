package com.sneakershop.SneakerShop.controllers.api;

import com.sneakershop.SneakerShop.core.dto.request.UserSignInDTO;
import com.sneakershop.SneakerShop.core.dto.request.UserSignUpDTO;
import com.sneakershop.SneakerShop.security.jwt.JwtAuthenticationResponse;
import com.sneakershop.SneakerShop.services.business.IAuthenticationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationApiController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IAuthenticationService authenticationService;

    @Autowired
    public AuthenticationApiController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@Valid @RequestBody UserSignUpDTO user) {
        logger.info("Perform sign up operation for new user with phone={}", user.getPhoneNumber());
        JwtAuthenticationResponse JWTToken = authenticationService.signUp(user);
        return ResponseEntity.status(HttpStatus.OK).body(JWTToken);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> signIn(@Valid @RequestBody UserSignInDTO user) {
        logger.info("Perform sign in operation for user with identifier={}", user.getIdentifier());
        JwtAuthenticationResponse JWTToken = authenticationService.signIn(user);
        return ResponseEntity.status(HttpStatus.OK).body(JWTToken);
    }
}
