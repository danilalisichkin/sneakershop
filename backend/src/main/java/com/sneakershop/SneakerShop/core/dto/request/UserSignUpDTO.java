package com.sneakershop.SneakerShop.core.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignUpDTO {

    @NotNull
    @NotBlank
    @Size(min = 1, max = 30)
    @Pattern(regexp = "^[a-zA-Z]{1,30}$")
    private String firstName;

    @NotNull
    @NotBlank
    @Size
    @Pattern(regexp = "^[a-zA-Z]{1,30}$")
    private String lastName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "illegal format of email,correct example: email@mail.org , google@gmail.com")
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\+375(15|29|33|44)\\d{7}$",
            message = "illegal format of phone number, correct example: +375291234567")
    private String phoneNumber;

    @NotNull
    @NotBlank
    @Size(min = 4, max = 24)
    @Pattern(regexp = "^[a-zA-Z0-9]{4,24}$",
            message = "illegal format of password, correct example: SnEAKer1Head33")
    private String password;

    @NotNull
    @NotBlank
    private String confirmPassword;

    @NotNull
    @NotBlank
    @AssertTrue
    private Boolean privacyAgreement;

    @NotNull
    @NotBlank
    private Boolean advertisingAgreement;
}
