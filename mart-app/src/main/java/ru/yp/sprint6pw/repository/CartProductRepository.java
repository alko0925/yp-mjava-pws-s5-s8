package ru.yp.sprint6pw.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.yp.sprint6pw.model.CartProduct;

@Repository
public interface CartProductRepository extends ReactiveCrudRepository<CartProduct, Integer> {

    @Query(value = "INSERT INTO mart_app.cart_products (cart_id, product_id, quantity) VALUES (:cart_id, :product_id, 1)")
    Mono<CartProduct> saveByCartIdAndProductId(Integer cart_id, Integer product_id);

    @Query(value = "UPDATE mart_app.cart_products SET quantity = :quantity WHERE cart_products.cart_id = :cart_id AND product_id = :product_id")
    Mono<CartProduct> saveByCartIdAndProductId(Integer cart_id, Integer product_id, Integer quantity);

    @Query(value = "DELETE FROM mart_app.cart_products WHERE cart_products.cart_id = :cart_id AND product_id = :product_id")
    Mono<CartProduct> deleteByCartIdAndProductId(Integer cart_id, Integer product_id);
}