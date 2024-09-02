package com.sneakershop.SneakerShop.controllers.api;

import com.sneakershop.SneakerShop.core.dto.request.CartPlacingDTO;
import com.sneakershop.SneakerShop.core.dto.request.ProductAddingToCartDTO;
import com.sneakershop.SneakerShop.core.dto.request.ProductRemovingFromCartDTO;
import com.sneakershop.SneakerShop.core.dto.response.ProductInCartDTO;
import com.sneakershop.SneakerShop.core.dto.response.ShoppingCartDTO;
import com.sneakershop.SneakerShop.core.enums.PaymentMethod;
import com.sneakershop.SneakerShop.services.business.IShoppingCartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartApiController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartApiController(IShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/add-product")
    public ResponseEntity<Object> addProductToShoppingCart(
            @Valid @RequestBody ProductAddingToCartDTO addingToCartDTO) {

        logger.info("Adding product with id={} to shopping cart for client with phone={}...",
                addingToCartDTO.getProductId(), addingToCartDTO.getClientPhoneNumber());

        shoppingCartService.addProductToClientShoppingCart(addingToCartDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/remove-product")
    public ResponseEntity<Object> removeProductFromShoppingCart(
            @Valid @RequestBody ProductRemovingFromCartDTO removingFromCartDTO) {

        logger.info("Removing {} pc. of product with id={} from shopping cart for client with phone={}...",
                removingFromCartDTO.getQuantity(), removingFromCartDTO.getClientPhoneNumber());

        shoppingCartService.removeProductFromClientShoppingCart(removingFromCartDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/items")
    public ResponseEntity<Object> getItemsInShoppingCart(
            @Pattern(regexp = "^375(15|29|33|44)\\d{7}$")
            @RequestParam(name = "phone") String phoneNumber) {

        phoneNumber = "+" + phoneNumber;
        logger.info("Getting items in cart for client with phone={}", phoneNumber);

        List<ProductInCartDTO> items = shoppingCartService.getItemsInClientShoppingCart(phoneNumber);

        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getClientShoppingCart(
            @Pattern(regexp = "^375(15|29|33|44)\\d{7}$")
            @RequestParam(name = "phone") String phoneNumber) {

        phoneNumber = "+" + phoneNumber;
        logger.info("Getting shopping cart for client with phone={}", phoneNumber);

        ShoppingCartDTO cart = shoppingCartService.getClientShoppingCartDTO(phoneNumber);

        return ResponseEntity.status(HttpStatus.OK).body(cart);
    }

    @PostMapping("/place")
    public ResponseEntity<Object> placeOrderFromShoppingCart(
            @Valid @RequestBody CartPlacingDTO cartPlacingDTO) {

        String phoneNumber = cartPlacingDTO.getClientPhoneNumber();
        phoneNumber = "+" + phoneNumber;
        cartPlacingDTO.setClientPhoneNumber(phoneNumber);
        logger.info("Placing order from shopping cart for client with phone={}", phoneNumber);

        shoppingCartService.signClientShoppingCartPlacing(cartPlacingDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
