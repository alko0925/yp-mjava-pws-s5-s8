package ru.yp.sprint5pw.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yp.sprint5pw.model.Cart;
import ru.yp.sprint5pw.model.Product;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query(value = "SELECT c FROM Cart c WHERE c.userId = ?1")
    Cart findCartByUserId(Integer user_id);
}
