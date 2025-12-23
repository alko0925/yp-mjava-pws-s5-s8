package ru.yp.sprint6pw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yp.sprint6pw.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}