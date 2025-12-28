package ru.yp.sprint6pw.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.yp.sprint6pw.model.Cart;

@Repository
public interface CartRepository extends ReactiveCrudRepository<Cart, Integer> {
    @Query(value = "SELECT c FROM Cart c WHERE c.userId = ?1")
    Mono<Cart> findCartByUserId(Integer user_id);
}
