package ru.yp.sprint5pw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yp.sprint5pw.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}