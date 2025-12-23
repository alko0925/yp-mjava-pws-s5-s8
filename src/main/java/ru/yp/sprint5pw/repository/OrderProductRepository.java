package ru.yp.sprint5pw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yp.sprint5pw.model.*;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {
}
