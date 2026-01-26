package ru.yp.sprint7pw.integration.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.yp.sprint7pw.MyMarketApplicationTest;
import ru.yp.sprint7pw.model.Cart;
import ru.yp.sprint7pw.model.Order;
import ru.yp.sprint7pw.model.Product;
import ru.yp.sprint7pw.repository.*;

public class MyMarketApplicationWebTest extends MyMarketApplicationTest {
    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected CartRepository cartRepository;

    @Autowired
    protected CartProductRepository cartProductRepository;

    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    protected OrderProductRepository orderProductRepository;


    @BeforeEach
    void setup() {
        productRepository.deleteAll().block();

        Product p1 = productRepository.save(new Product("Prod1", "", "", 1000L)).block();
        Product p2 = productRepository.save(new Product("Prod2", "", "", 2000L)).block();
        Product p3 = productRepository.save(new Product("Prod3", "", "", 3000L)).block();

        Integer userId = 1;
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart = cartRepository.save(cart).block();

        cartProductRepository.saveByCartIdAndProductId(cart.getId(), p1.getId())
                .then(cartProductRepository.saveByCartIdAndProductId(cart.getId(), p2.getId()))
                .then(cartProductRepository.saveByCartIdAndProductId(cart.getId(), p1.getId(), 2))
                .then(cartProductRepository.saveByCartIdAndProductId(cart.getId(), p2.getId(), 4)).block();

        Order order = new Order();
        order.setUserId(userId);
        order = orderRepository.save(order).block();

        orderProductRepository.saveByOrderIdAndProductId(order.getId(), p1.getId(), 5)
                .then(orderProductRepository.saveByOrderIdAndProductId(order.getId(), p3.getId(), 10)).block();
    }

    @AfterEach
    void cleanUp() {
        cartProductRepository.deleteAll()
                .then(cartRepository.deleteAll())
                .then(orderProductRepository.deleteAll())
                .then(orderRepository.deleteAll())
                .then(productRepository.deleteAll())
                .block();
    }
}
