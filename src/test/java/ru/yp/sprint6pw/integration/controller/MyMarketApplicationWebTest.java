package ru.yp.sprint6pw.integration.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yp.sprint6pw.MyMarketApplicationTest;
import ru.yp.sprint6pw.model.*;
import ru.yp.sprint6pw.repository.*;

public class MyMarketApplicationWebTest extends MyMarketApplicationTest {
    @Autowired
    protected WebApplicationContext wac;

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

    protected MockMvc mockMvc;

//    @BeforeEach
//    void setup() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//
//        productRepository.deleteAll();
//
//        Integer userId = 1;
//        Product p1 = productRepository.save(new Product("Prod1", "", "", 1000L));
//        Product p2 = productRepository.save(new Product("Prod2", "", "", 2000L));
//        Product p3 = productRepository.save(new Product("Prod3", "", "", 3000L));
//
//
//        Cart cart = new Cart();
//        cart.setUserId(userId);
//        cart = cartRepository.save(cart);
//        CartProduct cp1 = cartProductRepository.save(new CartProduct(cart, p1, 3));
//        CartProduct cp2 = cartProductRepository.save(new CartProduct(cart, p2, 6));
//        cart.getCartProducts().add(cp1);
//        cart.getCartProducts().add(cp2);
//        cartRepository.save(cart);
//
//        Order order = new Order();
//        order.setUserId(userId);
//        order = orderRepository.save(order);
//        OrderProduct op1 = orderProductRepository.save(new OrderProduct(order, p1, 3));
//        OrderProduct op2 = orderProductRepository.save(new OrderProduct(order, p2, 6));
//        order.getOrderProducts().add(op1);
//        order.getOrderProducts().add(op2);
//        orderRepository.save(order);
//    }

//    @AfterEach
//    void cleanUp() {
//        cartProductRepository.deleteAll();
//        cartRepository.deleteAll();
//        orderProductRepository.deleteAll();
//        orderRepository.deleteAll();
//        productRepository.deleteAll();
//    }
}
