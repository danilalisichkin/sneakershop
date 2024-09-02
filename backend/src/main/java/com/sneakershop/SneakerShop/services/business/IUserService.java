package com.sneakershop.SneakerShop.services.business;

import com.sneakershop.SneakerShop.entities.User;

public interface IUserService {
    void createUser(User user);

    User updateUser(String phone, User user);

    void deleteUser(String phone);

    User getUserByPhone(String phone);

    User getUserByEmail(String email);
}
