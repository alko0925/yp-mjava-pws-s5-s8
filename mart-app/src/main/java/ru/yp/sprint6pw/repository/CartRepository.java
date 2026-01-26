package ru.yp.sprint6pw.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.yp.sprint6pw.model.Cart;

@Repository
public interface CartRepository extends ReactiveCrudRepository<Cart, Integer> {
    @Query(value = "SELECT id, user_id FROM mart_app.carts c WHERE c.user_id = :user_id")
    Mono<Cart> findCartByUserId(Integer user_id);
}
