package ru.yp.sprint5pw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yp.sprint5pw.model.CartProduct;
import ru.yp.sprint5pw.model.CartProductPK;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, CartProductPK> {
}