package com.sneakershop.SneakerShop.core.mappers.user;

import com.sneakershop.SneakerShop.entities.User;
import com.sneakershop.SneakerShop.security.MyUserDetails;

import java.util.function.Function;

public class UserMapper {
    public static ToMyUserDetails toMyUserDetails = new ToMyUserDetails();

    public static class ToMyUserDetails implements Function<User, MyUserDetails> {

        @Override
        public MyUserDetails apply(User user) {
            if (user == null) return null;

            return MyUserDetails.builder()
                    .phoneNumber(user.getPhoneNumber())
                    .password(user.getPassword())
                    .role(user.getRole())
                    .isActive(user.getIsActive())
                    .build();
        }
    }
}
