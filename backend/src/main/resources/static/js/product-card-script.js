import * as navigationBar from './modules/navigation-bar.js';

main();

function main() {
    //requestToCreateTestOrder();
    navigationBar.addActiveStyleForButtons();
    navigationBar.addEventListenersForButtons();
    addEventListenerForAddToCartButton();
}

function addToCart(productId) {
    const clientPhoneNumber = '+375292884988';
    const quantity = 1;
    const url = navigationBar.rootUrl + 'products/' + productId + '/add_to_cart';

    const data = {
        clientPhoneNumber: clientPhoneNumber,
        quantity: quantity
    };

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        console.log('Success:', data);
        // Здесь можно обновить UI, например, показать сообщение об успешном добавлении товара в корзину
    })
    .catch(error => {
        console.error('Error:', error);
        // Здесь можно показать сообщение об ошибке
    });
}

function addEventListenerForAddToCartButton() {
    let button = document.getElementById('add-to-cart-button');
    let productId = button.dataset.productId;
    console.log("Adding event listener to button for product with id=" + productId);
    button.addEventListener('click', function() {
        addToCart(productId);
    });
}
