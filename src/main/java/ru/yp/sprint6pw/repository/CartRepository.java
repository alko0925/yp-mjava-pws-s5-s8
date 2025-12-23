package ru.yp.sprint6pw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yp.sprint6pw.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query(value = "SELECT c FROM Cart c WHERE c.userId = ?1")
    Cart findCartByUserId(Integer user_id);
}
