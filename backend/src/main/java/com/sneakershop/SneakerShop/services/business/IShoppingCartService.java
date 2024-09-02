package com.sneakershop.SneakerShop.services.business;

import com.sneakershop.SneakerShop.core.dto.request.CartPlacingDTO;
import com.sneakershop.SneakerShop.core.dto.request.ProductAddingToCartDTO;
import com.sneakershop.SneakerShop.core.dto.request.ProductRemovingFromCartDTO;
import com.sneakershop.SneakerShop.core.dto.response.ProductInCartDTO;
import com.sneakershop.SneakerShop.core.dto.response.ShoppingCartDTO;
import com.sneakershop.SneakerShop.core.enums.PaymentMethod;

import java.util.List;

public interface IShoppingCartService {

    ShoppingCartDTO addProductToClientShoppingCart(ProductAddingToCartDTO addingDTO);

    ShoppingCartDTO removeProductFromClientShoppingCart(ProductRemovingFromCartDTO removingDTO);

    ShoppingCartDTO getClientShoppingCartDTO(String clientPhoneNumber);

    List<ProductInCartDTO> getItemsInClientShoppingCart(String clientPhoneNumber);

    void signClientShoppingCartPlacing(CartPlacingDTO placingDTO);
}
