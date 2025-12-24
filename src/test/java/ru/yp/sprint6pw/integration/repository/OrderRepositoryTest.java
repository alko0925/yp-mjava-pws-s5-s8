package ru.yp.sprint6pw.integration.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @BeforeEach
//    void setUp() {
//        orderRepository.deleteAll();
//
//        Integer userId = 1;
//        Order order = new Order();
//        order.setUserId(userId);
//        orderRepository.save(order);
//
//        Product p1 = productRepository.save(new Product("Prod1", "", "", 1000L));
//        OrderProduct op1 = orderProductRepository.save(new OrderProduct(order, p1, 3));
//        order.getOrderProducts().add(op1);
//
//        orderRepository.save(order);
//    }

//    @Test
//    @Transactional
//    void testFindCartByUserId() {
//        Integer userId = 1;
//        Order result = orderRepository.findAll().getFirst();
//
//        assertNotNull(result, "Order was not found");
//        assertEquals(userId, result.getUserId(), "Order of wrong User was retrieved");
//        assertEquals(1, result.getOrderProducts().size(), "Wrong number of products in order");
//        assertEquals(3, result.getOrderProducts().stream().filter(op -> op.getProduct().getTitle() == "Prod1").findFirst().get().getQuantity(), "Wrong quantity of specific product in order");
//    }

//    @AfterEach
//    void cleanUp() {
//        orderProductRepository.deleteAll();
//        orderRepository.deleteAll();
//        productRepository.deleteAll();
//    }
}