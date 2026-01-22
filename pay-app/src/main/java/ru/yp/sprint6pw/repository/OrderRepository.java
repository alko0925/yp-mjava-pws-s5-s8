package ru.yp.sprint6pw.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.yp.sprint6pw.model.Order;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Integer> {
}