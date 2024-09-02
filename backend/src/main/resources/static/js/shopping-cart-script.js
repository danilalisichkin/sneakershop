import * as navigationBar from './modules/navigation-bar.js';
var cartItemList = [];

main();

function main() {
    navigationBar.addActiveStyleForButtons();
    navigationBar.addEventListenersForButtons();
    getCartItems();
}

function getCartItems() {
    let clientPhoneNumber = '+375292884988';
    $.ajax({
        url: navigationBar.rootUrl + 'cart/' + clientPhoneNumber,
        dataType: 'json',
        success: function(data) {
            cartItemList = data.cartItems;
            updateCartItemListHTML();
            updateTotalCartCost();
        },
        error: function(xhr, status, error) {
            console.error('Error while fetching cart items:', error);
            // Handle error, e.g., display an error message to the user
        }
    });
}

function updateCartItemListHTML() {
    $('#cart-items').empty();

    cartItemList.forEach(function(cartItem) {
        let productHTML = '<div class="product-in-cart">' +
            '<img src="' + (cartItem.product ? cartItem.product.shoeModel.imageUrl : '') + '" class="product-img">' +
            '<p class="product-title">' + (cartItem.product ? cartItem.product.shoeModel.brandName + ' ' + cartItem.product.shoeModel.modelName : '') + '</p>' +
            '<p class="product-size">' + (cartItem.product ? cartItem.product.size + ' eu' : '') + '</p>' +
            '<p class="product-price">' + (cartItem.product ? cartItem.product.price + '$' : '') + '</p>' +
            '<div class="product-options-str" style="border:none;">' +
            '<input type="number" id="quantity" name="quantity" min="1" value="' + (cartItem.quantity || 0) + '" class="product-options-form">' +
            '</div>' +
            '</div>';

        $('#cart-items').append(productHTML);
    });
}

function updateTotalCartCost() {
    let totalCost = 0;
    for (var i = 0; i < cartItemList.length; i++) {
        if (cartItemList[i].product) {
            totalCost += cartItemList[i].product.price * cartItemList[i].quantity;
        }
    }
    $('#cart-total-cost').text(totalCost);
}
