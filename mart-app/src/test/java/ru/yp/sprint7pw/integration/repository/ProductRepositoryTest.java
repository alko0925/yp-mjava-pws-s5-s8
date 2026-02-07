package ru.yp.sprint7pw.integration.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yp.sprint7pw.MartApplicationTest;
import ru.yp.sprint7pw.model.Product;
import ru.yp.sprint7pw.repository.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductRepositoryTest extends MartApplicationTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll()
                .then(productRepository.save(new Product("Prod1", "", "", 1000L)))
                .then(productRepository.save(new Product("Prod2", "", "", 2000L)))
                .then(productRepository.save(new Product("Prod3", "", "", 3000L)))
                .block();
    }

    @Test
    void testFindAll() {
        List<Product> result = productRepository.findAll().collectList().block();
        assertNotNull(result, "Product list is null");
        assertEquals(3, result.size());
    }

    @Test
    void testFindAllByCriterias() {
        String search = "Prod2";
        int pageNumber = 1;
        int pageSize = 5;

        String fsearch = ("%" + search + "%").toLowerCase();
        int offset = (pageNumber - 1) * pageSize;
        int limit = pageSize;

        List<Product> result = productRepository.findProductsByCriterias(fsearch, offset, limit).collectList().block();

        assertNotNull(result, "Product list is null");
        assertEquals(1, result.size(), "Wrong number of products was detected.");
        assertEquals(search, result.getFirst().getTitle(), "Wrong number of products was detected.");
    }

    @AfterEach
    void cleanUp() {
        productRepository.deleteAll()
                .block();
    }
}
