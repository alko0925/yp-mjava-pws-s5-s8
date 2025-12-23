package ru.yp.sprint6pw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yp.sprint6pw.model.CartProduct;
import ru.yp.sprint6pw.model.CartProductPK;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, CartProductPK> {
}