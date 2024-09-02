package com.sneakershop.SneakerShop.services.business.impl;

import com.sneakershop.SneakerShop.core.dto.request.CartPlacingDTO;
import com.sneakershop.SneakerShop.core.dto.request.ProductAddingToCartDTO;
import com.sneakershop.SneakerShop.core.dto.request.ProductRemovingFromCartDTO;
import com.sneakershop.SneakerShop.core.dto.response.ProductInCartDTO;
import com.sneakershop.SneakerShop.core.dto.response.ShoppingCartDTO;
import com.sneakershop.SneakerShop.core.enums.OrderStatus;
import com.sneakershop.SneakerShop.core.enums.PaymentMethod;
import com.sneakershop.SneakerShop.core.mappers.cart.ShoppingCartItemMapper;
import com.sneakershop.SneakerShop.core.mappers.cart.ShoppingCartMapper;
import com.sneakershop.SneakerShop.core.mappers.order.OrderItemMapper;
import com.sneakershop.SneakerShop.core.mappers.order.OrderMapper;
import com.sneakershop.SneakerShop.core.mappers.product.ProductMapper;
import com.sneakershop.SneakerShop.core.models.cart.ShoppingCart;
import com.sneakershop.SneakerShop.core.models.cart.ShoppingCartItem;
import com.sneakershop.SneakerShop.entities.Order;
import com.sneakershop.SneakerShop.entities.OrderItem;
import com.sneakershop.SneakerShop.entities.Product;
import com.sneakershop.SneakerShop.entities.User;
import com.sneakershop.SneakerShop.exceptions.BadRequestException;
import com.sneakershop.SneakerShop.exceptions.IllegalOperationException;
import com.sneakershop.SneakerShop.exceptions.InsufficientStockException;
import com.sneakershop.SneakerShop.exceptions.ResourceNotFoundException;
import com.sneakershop.SneakerShop.services.business.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService implements IShoppingCartService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IProductService productService;

    private final IOrderService orderService;

    private final IOrderItemService orderItemService;

    private final IUserService userService;

    @Autowired
    public ShoppingCartService(IOrderItemService orderItemService, IOrderService orderService, IProductService productService, IUserService userService) {
        this.orderItemService = orderItemService;
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public ShoppingCartDTO addProductToClientShoppingCart(ProductAddingToCartDTO addingDTO) {

        User client = userService.getUserByPhone(addingDTO.getClientPhoneNumber());

        Product product = productService.getProductById(addingDTO.getProductId());

        if (product.getQuantity() < addingDTO.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock for product with id={}", addingDTO.getProductId());
        }

        ShoppingCart shoppingCart = getClientShoppingCart(client);
        if (shoppingCart == null) {
            shoppingCart = createAndSaveNewEmptyCart(client);
        }

        saveProductToShoppingCart(shoppingCart, product, addingDTO.getQuantity());
        return ShoppingCartMapper.toDTO.apply(shoppingCart);
    }

    @Override
    public ShoppingCartDTO removeProductFromClientShoppingCart(ProductRemovingFromCartDTO removingDTO) {
        User client = userService.getUserByPhone(removingDTO.getClientPhoneNumber());

        ShoppingCart shoppingCart = getClientShoppingCart(client);
        if (shoppingCart == null) {
            throw new ResourceNotFoundException("Shopping cart for client with phone=%s not found", removingDTO.getClientPhoneNumber());
        }

        ShoppingCartItem matchingCartItem = shoppingCart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(removingDTO.getProductId()))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Shopping cart item with product id=%s not found for client with phone=%s",
                                removingDTO.getProductId(), removingDTO.getClientPhoneNumber()));

        if (matchingCartItem.getQuantity() < removingDTO.getQuantity()) {
            throw new BadRequestException("Quantity to remove is grater then current item quantity");
        }

        matchingCartItem.setQuantity(matchingCartItem.getQuantity() - removingDTO.getQuantity());
        if (matchingCartItem.getQuantity() == 0) {
            logger.info("Removing cart item with product id={} for client with phone={}", removingDTO.getProductId(), removingDTO.getClientPhoneNumber());
            orderItemService.deleteOrderItemById(matchingCartItem.getId());
            shoppingCart.getItems().remove(matchingCartItem);
        } else {
            logger.info("Removing {} pc. of cart item with product id={} for client with phone={}", removingDTO.getProductId(), removingDTO.getClientPhoneNumber(), removingDTO.getProductId());
            OrderItem orderItem = orderItemService.getOrderItemById(matchingCartItem.getId());
            orderItem.setQuantity(matchingCartItem.getQuantity());
            orderItemService.updateOrderItem(orderItem);
        }

        return ShoppingCartMapper.toDTO.apply(shoppingCart);
    }

    @Override
    public List<ProductInCartDTO> getItemsInClientShoppingCart(String clientPhoneNumber) {
        User client = userService.getUserByPhone(clientPhoneNumber);

        ShoppingCart cart = getClientShoppingCart(client);

        List<ProductInCartDTO> cartProductsDTO = cart.getItems().stream()
                .map(item -> {
                    ProductInCartDTO dto = ProductMapper.toInCartDTO.apply(item.getProduct());
                    dto.setQuantity(item.getQuantity());
                    return dto;
                })
                .collect(Collectors.toList());

        return cartProductsDTO;
    }

    @Override
    public ShoppingCartDTO getClientShoppingCartDTO(String clientPhoneNumber) {
        User client = userService.getUserByPhone(clientPhoneNumber);

        return ShoppingCartMapper.toDTO.apply(getClientShoppingCart(client));
    }

    @Override
    public void signClientShoppingCartPlacing(CartPlacingDTO placingDTO) {
        User client = userService.getUserByPhone(placingDTO.getClientPhoneNumber());

        List<Order> orders = orderService.getClientOrdersByStatus(client, OrderStatus.IN_CART);

        // проверку зареган ли юзер и есть ли у него корзина

        if (orders == null || orders.isEmpty()) {
            throw new IllegalOperationException("Can't place order for non existing cart");
        }

        if (orders.size() > 1) {
            throw new IllegalOperationException("Found >1 shopping carts for client with phone=%s", client.getPhoneNumber());
        }

        Order order = orders.get(0);

        order.setStatus(OrderStatus.PENDING);
        order.setDeliveryAddress(placingDTO.getDeliveryAddress());
        order.setPaymentMethod(placingDTO.getPaymentMethod());
        order.setCreationDate(ZonedDateTime.now(ZoneId.of("Europe/Moscow")));
        for (OrderItem orderItem : order.getItems()) {
            Product product = orderItem.getProduct();
            productService.setProductQuantity(
                    product,
                    product.getQuantity() - orderItem.getQuantity()
            );
        }

        orderService.saveOrder(order);
    }

    private ShoppingCart getClientShoppingCart(User client) {
        List<Order> orders = orderService.getClientOrdersByStatus(client, OrderStatus.IN_CART);

        if (orders == null || orders.isEmpty()) {
            return null;
        }

        if (orders.size() > 1) {
            throw new IllegalOperationException("Found >1 shopping carts for client with phone=%s", client.getPhoneNumber());
        }

        return OrderMapper.toShoppingCart.apply(orders.get(0));
    }

    private ShoppingCart createAndSaveNewEmptyCart(User client) {
        Order shoppingCartAsOrder = orderService.createNewOrder(client);

        shoppingCartAsOrder.setStatus(OrderStatus.IN_CART);
        Long id = orderService.saveOrder(shoppingCartAsOrder).getId();
        shoppingCartAsOrder.setId(id);

        ShoppingCart shoppingCart = OrderMapper.toShoppingCart.apply(shoppingCartAsOrder);

        return shoppingCart;
    }

    private ShoppingCart saveProductToShoppingCart(ShoppingCart shoppingCart, Product product, Integer quantity) {
        Order cartAsOrder = orderService.getOrderById(shoppingCart.getId());
        if (cartAsOrder == null) {
            throw new ResourceNotFoundException("Shopping cart for client with phone=%s does not exist", shoppingCart.getClient().getPhoneNumber());
        }

        Optional<ShoppingCartItem> matchingCartItem = shoppingCart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().equals(product))
                .findFirst();

        ShoppingCartItem cartItem;
        if (matchingCartItem.isPresent()) {
            logger.info("Adding %s more pc. for product with id=%s to shopping cart for client with id=%s", quantity, product.getId(), shoppingCart.getClient().getPhoneNumber());
            cartItem = matchingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            logger.info("Adding new product with id=%s to shopping cart for client with id=%s", product.getId(), shoppingCart.getClient().getPhoneNumber());
            cartItem = ShoppingCartItem.builder()
                    .id(null)
                    .product(product)
                    .quantity(quantity)
                    .build();
        }

        OrderItem orderItem = ShoppingCartItemMapper.toOrderItem.apply(cartItem);
        orderItem = orderItemService.saveOrderItem(orderItem, cartAsOrder);

        if (matchingCartItem.isPresent()) {
            cartItem.setQuantity(quantity);
        } else {
            shoppingCart.getItems().add(OrderItemMapper.toShoppingCartItem.apply(orderItem));
        }

        return shoppingCart;
    }
}
