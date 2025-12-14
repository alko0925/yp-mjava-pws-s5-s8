package ru.yp.sprint5pw.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yp.sprint5pw.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT p FROM Product p WHERE LOWER(p.title) LIKE ?1 OR LOWER(p.description) LIKE ?1")
    Page<Product> findProductsWithCriterias(String search, Pageable pageable);
}
