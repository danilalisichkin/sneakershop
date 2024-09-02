package com.sneakershop.SneakerShop.services.business.impl;

import com.sneakershop.SneakerShop.core.dto.request.UserSignInDTO;
import com.sneakershop.SneakerShop.core.dto.request.UserSignUpDTO;
import com.sneakershop.SneakerShop.core.enums.Role;
import com.sneakershop.SneakerShop.core.mappers.user.UserMapper;
import com.sneakershop.SneakerShop.dao.repository.UserDetailsRepository;
import com.sneakershop.SneakerShop.dao.repository.UserRepository;
import com.sneakershop.SneakerShop.entities.User;
import com.sneakershop.SneakerShop.entities.UserDetails;
import com.sneakershop.SneakerShop.exceptions.BadRequestException;
import com.sneakershop.SneakerShop.exceptions.ResourceNotFoundException;
import com.sneakershop.SneakerShop.security.MyUserDetails;
import com.sneakershop.SneakerShop.security.MyUserDetailsService;
import com.sneakershop.SneakerShop.security.jwt.JwtAuthenticationResponse;
import com.sneakershop.SneakerShop.security.jwt.JwtUtil;
import com.sneakershop.SneakerShop.services.business.IAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String emailValidationPattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    private final String phoneValidationPattern = "^\\+375(15|29|33|44)\\d{7}$";

    private final UserRepository userRepository;

    private final UserDetailsRepository userDetailsRepository;

    private final MyUserDetailsService myUserDetailsService;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository, UserDetailsRepository userDetailsRepository, MyUserDetailsService myUserDetailsService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public JwtAuthenticationResponse signUp(UserSignUpDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        if (userRepository.findById(userDTO.getPhoneNumber()).isPresent()) {
            throw new BadRequestException("User with phone=%s already exists", userDTO.getPhoneNumber());
        } else if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new BadRequestException("User with email=%s already exists", userDTO.getEmail());
        }

        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .phoneNumber(userDTO.getPhoneNumber())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.ROLE_CUSTOMER)
                .build();

        user = userRepository.save(user);

        UserDetails userDetails = UserDetails.builder()
                .user(user)
                .privacyAgreement(userDTO.getPrivacyAgreement())
                .adAgreement(userDTO.getAdvertisingAgreement())
                .totalRedemption(BigDecimal.ZERO)
                .totalRefund(BigDecimal.ZERO)
                .discount(BigDecimal.ZERO)
                .build();

        userDetailsRepository.save(userDetails);

        String jwt = jwtUtil.generateToken(UserMapper.toMyUserDetails.apply(user));
        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    public JwtAuthenticationResponse signIn(UserSignInDTO userDTO) {
        String userIdentifier = userDTO.getIdentifier();
        User userEntity;

        if (userIdentifier.matches(phoneValidationPattern)) {
            userEntity = userRepository.findById(userIdentifier).get();
            if (userEntity == null) {
                throw new ResourceNotFoundException("User with phone=%s not found", userIdentifier);
            }
        } else if (userIdentifier.matches(emailValidationPattern)) {
            userEntity = userRepository.findByEmail(userIdentifier).orElseThrow(
                    () -> new ResourceNotFoundException("User with email=%s not found", userIdentifier)
            );
        } else {
            throw new BadRequestException("incorrect format of login/email");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userEntity.getPhoneNumber(),
                userDTO.getPassword()
        ));

        MyUserDetails userDetails = myUserDetailsService.loadUserByUsername(userEntity.getPhoneNumber());

        String jwt = jwtUtil.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwt);
    }
}
