package ru.yp.sprint7pw.integration.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.MyMarketApplicationTest;
import ru.yp.sprint7pw.model.Cart;
import ru.yp.sprint7pw.repository.CartProductRepository;
import ru.yp.sprint7pw.repository.CartRepository;
import ru.yp.sprint7pw.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CartRepositoryTest extends MyMarketApplicationTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        cartRepository.deleteAll()
                .then(Mono.fromCallable(() -> {
                    Integer userId = 1;
                    Cart cart = new Cart();
                    cart.setUserId(userId);
                    return cart;
                }))
                .flatMap(cart -> cartRepository.save(cart))
                .block();
    }

    @Test
    void testFindCartByUserId() {
        Integer userId = 1;
        Cart result = cartRepository.findCartByUserId(userId).block();

        assertNotNull(result, "Cart was not found");
        assertEquals(userId, result.getUserId(), "Cast of wrong User was retrieved");
    }

    @AfterEach
    void cleanUp() {
        cartProductRepository.deleteAll()
                .then(cartRepository.deleteAll())
                .then(productRepository.deleteAll())
                .block();
    }
}