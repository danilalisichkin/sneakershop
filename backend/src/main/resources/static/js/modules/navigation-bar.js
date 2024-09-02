export const rootUrl = "/sneaker_shop/v1/";

export function redirectToHomePage() {
    console.log("Redirecting to home page...");
    var url = rootUrl;
    window.location.href = url;
}

export function redirectToLoginPage() {
    console.log("Redirecting to login page...");
    var url = rootUrl + "login/";
    window.location.href = url;
}

export function redirectToProductList() {
    console.log("Redirecting to product list...");
    var url = rootUrl + "products/";
    window.location.href = url;
}

export function redirectToSignUpPage() {
    console.log("Redirecting to signup page...");
    var url = rootUrl + "signup/";
    window.location.href = url;
}

export function redirectToShoppingCart() {
    console.log("Redirecting to shopping cart...");
    const clientPhoneNumber = '+375292884988';
    const url = rootUrl + 'cart/' + clientPhoneNumber;
    window.location.href = url;
}

export function addEventListenersForButtons() {
    document.getElementById('home-button').addEventListener('click', () => {
        redirectToHomePage();
    });
    document.getElementById('products-button').addEventListener('click', () => {
        redirectToProductList();
    });
    document.getElementById('cart-button').addEventListener('click', () => {
        redirectToShoppingCart();
    });
    document.getElementById('login-button').addEventListener('click', () => {
        redirectToLoginPage();
    });
    document.getElementById('signup-button').addEventListener('click', () => {
        redirectToSignUpPage();
    });

    let activeCustomButtons = document.querySelectorAll('.custom-button.active-button').forEach(item => {
        item.addEventListener('click', () => {
            location.reload();
        });
    });
}

export function addActiveStyleForButtons() {
    let currentPath = window.location.pathname;
    let item;
    console.log("Current url=" + currentPath);
    if (currentPath === (rootUrl)) {
        item = document.getElementById('home-button');
    } else if (currentPath.startsWith(rootUrl + 'products/')) {
        item = document.getElementById('products-button');
    } else if (currentPath.startsWith(rootUrl + 'cart/')) {
        item = document.getElementById('cart-button');
    } else if (currentPath.startsWith(rootUrl + 'login/')) {
        item = document.getElementById('login-button');
    } else if (currentPath.startsWith(rootUrl + 'signup/')) {
        item = document.getElementById('signup-button');
    } else return;
    item.classList.add("active-button");
}
