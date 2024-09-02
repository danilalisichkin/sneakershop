import * as navigationBar from './modules/navigation-bar.js';

main();

function main() {
    addEventListenersForProductTiles();
    navigationBar.addActiveStyleForButtons();
    navigationBar.addEventListenersForButtons();
}

window.redirectToProductCard = function(productId) {
    console.log("Redirecting to product card...");
    var url = "/sneaker_shop/v1/" + "products/" + productId;
    window.location.href = url;
}

function addEventListenersForProductTiles() {
    document.querySelectorAll('.product-in-list').forEach(item => {
        console.log("Added!");
        var prevColor =  item.style.backgroundColor;
        var initialWidth = item.offsetWidth;
        item.addEventListener('mouseenter', (event) => {
            event.target.style.backgroundColor = "#BBC6C8";
        });
        item.addEventListener('mouseleave', (event) => {
            event.target.style.backgroundColor = prevColor;
        });
    });
}
