package ru.yp.sprint5pw.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yp.sprint5pw.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
