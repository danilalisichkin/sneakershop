package com.sneakershop.SneakerShop.services.business.impl;

import com.sneakershop.SneakerShop.dao.repository.UserRepository;
import com.sneakershop.SneakerShop.entities.User;
import com.sneakershop.SneakerShop.exceptions.BadRequestException;
import com.sneakershop.SneakerShop.exceptions.IllegalOperationException;
import com.sneakershop.SneakerShop.exceptions.ResourceNotFoundException;
import com.sneakershop.SneakerShop.services.business.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        logger.info("Creating user: with phone={}", user.getPhoneNumber());
        if (userRepository.findById(user.getPhoneNumber()).isPresent()) {
            throw new BadRequestException("User with phone=%s already exists", user.getPhoneNumber());
        } else if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new BadRequestException("User with email=%s already exists", user.getEmail());
        }

        userRepository.save(user);
    }

    @Override
    public User updateUser(String phone, User user) {
        if (!user.getPhoneNumber().equals(phone))
            throw new IllegalOperationException("Changing of phone number is not allowed");

        User previous = getUserByPhone(phone);

        Optional<User> other = userRepository.findByEmail(previous.getEmail());
        if (other.isPresent() && !other.get().getPhoneNumber().equals(phone)) {
            throw new BadRequestException("User with email=%s already exists", user.getEmail());
        }

        return userRepository.save(previous);
    }

    @Override
    public void deleteUser(String phone) {
        userRepository.findById(phone);
        userRepository.deleteById(phone);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userRepository.findById(phone).orElseThrow(() ->
                new ResourceNotFoundException("User with phone=%s does not exist", phone));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("User with email=%s does not exist", email));
    }
}
