package ru.yp.sprint6pw.integration.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.yp.sprint6pw.MyMarketApplicationTest;
import ru.yp.sprint6pw.model.Product;
import ru.yp.sprint6pw.repository.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductRepositoryTest extends MyMarketApplicationTest {

    @Autowired
    private ProductRepository productRepository;

//    @BeforeEach
//    void setUp() {
//        productRepository.deleteAll();
//        productRepository.save(new Product("Prod1", "", "", 1000L));
//        productRepository.save(new Product("Prod2", "", "", 2000L));
//        productRepository.save(new Product("Prod3", "", "", 3000L));
//    }

//    @Test
//    void testFindAll() {
//        List<Product> result = productRepository.findAll();
//        assertNotNull(result, "Product list is null");
//        assertEquals(3, result.size());
//    }

//    @Test
//    void testFindAllByCriterias() {
//        String search = "Prod2";
//        int pageNumber = 0;
//        int pageSize = 5;
//
//        Pageable pageRequest = PageRequest.of( pageNumber, pageSize);
//        Page<Product> result = productRepository.findProductsWithCriterias(("%" + search + "%").toLowerCase(), pageRequest);
//
//        assertNotNull(result, "Product list is null");
//        assertEquals(1, result.getContent().size(), "Wrong number of products was detected.");
//        assertEquals(search, result.getContent().getFirst().getTitle(), "Wrong number of products was detected.");
//    }

//    @AfterEach
//    void cleanUp() {
//        productRepository.deleteAll();
//    }
}
