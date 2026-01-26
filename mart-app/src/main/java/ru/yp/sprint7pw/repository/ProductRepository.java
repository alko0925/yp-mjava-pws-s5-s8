package ru.yp.sprint7pw.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.model.Product;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {

    @Query(value = """
            SELECT count(1) FROM mart_app.products p
            WHERE LOWER(p.title) LIKE :search OR LOWER(p.description) LIKE :search
            """
    )
    Mono<Integer> countProductsByCriterias(String search);

    @Query(value = """
            SELECT id, title, description, img_path, price
            FROM mart_app.products p
            WHERE LOWER(p.title) LIKE :search OR LOWER(p.description) LIKE :search
            OFFSET :offset LIMIT :limit
            """
    )
    Flux<Product> findProductsByCriterias(String search, Integer offset, Integer limit);

    @Query(value = """
            SELECT id, title, description, img_path, price
            FROM mart_app.products p
            WHERE LOWER(p.title) LIKE :search OR LOWER(p.description) LIKE :search
            ORDER BY title
            OFFSET :offset LIMIT :limit
            """
    )
    Flux<Product> findProductsByCriteriasOrderByTitle(String search, Integer offset, Integer limit);

    @Query(value = """
            SELECT id, title, description, img_path, price
            FROM mart_app.products p
            WHERE LOWER(p.title) LIKE :search OR LOWER(p.description) LIKE :search
            ORDER BY price
            OFFSET :offset LIMIT :limit
            """
    )
    Flux<Product> findProductsByCriteriasOrderByPrice(String search, Integer offset, Integer limit);

}
