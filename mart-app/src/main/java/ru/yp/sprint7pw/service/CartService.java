package ru.yp.sprint7pw.service;

import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.model.Cart;
import ru.yp.sprint7pw.model.CartProduct;
import ru.yp.sprint7pw.model.Product;

public interface CartService {
    Mono<Cart> getCartByUserId(Integer userId);
    Mono<CartProduct> setProductToCartProduct(CartProduct cartProduct);
    Mono<Void> decreaseProductCount(Integer userId, Product product);
    Mono<Void> increaseProductCount(Integer userId, Product product);
    Mono<Void> deleteProduct(Integer userId, Product product);
    Mono<Void> delete(Cart cart);
}
