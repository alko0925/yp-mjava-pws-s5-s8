package ru.yp.sprint6pw.integration.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yp.sprint6pw.MyMarketApplicationTest;
import ru.yp.sprint6pw.model.Cart;
import ru.yp.sprint6pw.model.CartProduct;
import ru.yp.sprint6pw.model.Product;
import ru.yp.sprint6pw.repository.CartProductRepository;
import ru.yp.sprint6pw.repository.CartRepository;
import ru.yp.sprint6pw.repository.ProductRepository;


import static org.junit.jupiter.api.Assertions.*;

class CartRepositoryTest extends MyMarketApplicationTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        cartRepository.deleteAll();

        Integer userId = 1;
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart = cartRepository.save(cart);

        Product p1 = productRepository.save(new Product("Prod1", "", "", 1000L));
        Product p2 = productRepository.save(new Product("Prod2", "", "", 2000L));

        CartProduct cp1 = cartProductRepository.save(new CartProduct(cart, p1, 3));
        CartProduct cp2 = cartProductRepository.save(new CartProduct(cart, p2, 6));

        cart.getCartProducts().add(cp1);
        cart.getCartProducts().add(cp2);

        cartRepository.save(cart);
    }

    @Test
    @Transactional
    void testFindCartByUserId() {
        Integer userId = 1;
        Cart result = cartRepository.findCartByUserId(userId);

        assertNotNull(result, "Cart was not found");
        assertEquals(userId, result.getUserId(), "Cast of wrong User was retrieved");
        assertEquals(2, result.getCartProducts().size(), "Wrong number of products in cart");
        assertEquals(6, result.getCartProducts().stream().filter(cp -> cp.getProduct().getTitle() == "Prod2").findFirst().get().getQuantity(), "Wrong quantity of specific product in cart");
    }

    @AfterEach
    void cleanUp() {
        cartProductRepository.deleteAll();
        cartRepository.deleteAll();
        productRepository.deleteAll();
    }
}