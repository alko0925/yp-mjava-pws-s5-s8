package ru.yp.sprint6pw.integration.repository;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import ru.yp.sprint6pw.MyMarketApplicationTest;
import ru.yp.sprint6pw.model.*;
import ru.yp.sprint6pw.repository.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderRepositoryTest extends MyMarketApplicationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll()
                .then(Mono.fromCallable(() -> {
                    Integer userId = 1;
                    Order order = new Order();
                    order.setUserId(userId);
                    return order;
                }))
                .flatMap(order -> orderRepository.save(order))
                .block();
    }

    @Test
    void testFindOrderByUserId() {
        Integer userId = 1;
        Order result = orderRepository.findAll().blockFirst();

        assertNotNull(result, "Order was not found");
        assertEquals(userId, result.getUserId(), "Order with wrong user id was retrieved");
    }

    @AfterEach
    void cleanUp() {
        orderProductRepository.deleteAll()
                .then(orderRepository.deleteAll())
                .then(productRepository.deleteAll())
                .block();
    }
}