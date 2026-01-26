package ru.yp.sprint6pw.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.yp.sprint6pw.model.*;

@Repository
public interface OrderProductRepository extends ReactiveCrudRepository<OrderProduct, Integer> {
    @Query(value = "INSERT INTO mart_app.order_products (order_id, product_id, quantity) VALUES (:order_id, :product_id, :quantity)")
    Mono<OrderProduct> saveByOrderIdAndProductId(Integer cart_id, Integer product_id, Integer quantity);

}
