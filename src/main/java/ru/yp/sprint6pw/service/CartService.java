package ru.yp.sprint6pw.service;

import reactor.core.publisher.Mono;
import ru.yp.sprint6pw.model.Cart;
import ru.yp.sprint6pw.model.Product;

public interface CartService {
    Mono<Cart> getCartByUserId(Integer userId);
    Mono<Void> decreaseProductCount(Integer userId, Product product);
    Mono<Void> increaseProductCount(Integer userId, Product product);
    Mono<Void> deleteProduct(Integer userId, Product product);
    Mono<Void> delete(Cart cart);
}
