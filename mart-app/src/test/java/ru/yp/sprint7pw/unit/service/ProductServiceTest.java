package ru.yp.sprint7pw.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.MartApplicationTest;
import ru.yp.sprint7pw.model.Product;
import ru.yp.sprint7pw.repository.ProductRepository;
import ru.yp.sprint7pw.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ProductServiceTest extends MartApplicationTest {

    @MockitoBean
    private ProductRepository productRepository;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @Autowired
    private ProductService productService;

    @BeforeEach
    void resetMocks() {
        reset(productRepository);
    }

    @Test
    void testGetAllProducts() {
        Product p1 = new Product(1, "Prod1", "", "", 1000L);
        Product p2 = new Product(2, "Prod2", "", "", 2000L);

        List<Product> products = new ArrayList<>();
        products.add(p1);
        products.add(p2);

        doReturn(Flux.fromIterable(products)).when(productRepository).findAll();

        List<Product> result = productService.getAllProducts().collectList().block();

        assertNotNull(result, "Product list should not be null");
        assertEquals(products.size(), result.size(), "Wrong size of retrieved Orders list");
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProduct() {
        Integer productId = 1;
        Product p1 = new Product(productId, "Prod1", "", "", 1000L);

        doReturn(Mono.just(p1)).when(productRepository).findById(productId);

        Product result = productService.getProduct(productId).block();

        assertNotNull(p1, "Product was not found");
        assertEquals(productId, result.getId(), "Product with wrong id was retrieved");
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testCreate() {
        Integer productId = 1;
        Product p1 = new Product(productId, "Prod1", "", "", 1000L);

        doReturn(Mono.just(p1)).when(productRepository).save(p1);
        Product result = productService.create(p1).block();

        assertNotNull(p1, "Product was not created");
        verify(productRepository, times(1)).save(p1);
    }

    @Test
    void testUpdate() {
        Integer productId = 1;
        Product p1 = new Product(productId, "Prod1", "", "", 1000L);

        doReturn(Mono.just(p1)).when(productRepository).save(p1);
        productService.update(p1).block();

        verify(productRepository, times(1)).save(p1);
    }
}
