package com.sneakershop.SneakerShop.security;

import com.sneakershop.SneakerShop.core.mappers.user.UserMapper;
import com.sneakershop.SneakerShop.dao.repository.UserRepository;
import com.sneakershop.SneakerShop.exceptions.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public MyUserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return UserMapper.toMyUserDetails.apply(userRepository.findById(phone)
                .orElseThrow(() -> new ResourceNotFoundException("User with phone=%s not found", phone)));
    }
}
