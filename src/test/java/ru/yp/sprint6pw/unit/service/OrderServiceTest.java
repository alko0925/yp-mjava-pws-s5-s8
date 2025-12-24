package ru.yp.sprint6pw.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.yp.sprint6pw.MyMarketApplicationTest;
import ru.yp.sprint6pw.model.*;
import ru.yp.sprint6pw.repository.OrderProductRepository;
import ru.yp.sprint6pw.repository.OrderRepository;
import ru.yp.sprint6pw.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class OrderServiceTest extends MyMarketApplicationTest {

    @MockitoBean
    private OrderRepository orderRepository;

    @MockitoBean
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderService orderService;

//    @BeforeEach
//    void resetMocks() {
//        reset(orderRepository, orderProductRepository);
//    }

//    @Test
//    void testGetAllOrders() {
//        Integer userId = 1;
//        Order o1 = new Order();
//        Order o2 = new Order();
//
//        List<Order> orders = new ArrayList<>();
//        orders.add(o1);
//        orders.add(o2);
//
//        doReturn(orders).when(orderRepository).findAll();
//        List<Order> result = orderService.getAllOrders();
//
//        assertNotNull(result, "Order list should not be null");
//
//        assertEquals(orders.size(), result.size(), "Wrong size of retrieved Orders list");
//        verify(orderRepository, times(1)).findAll();
//    }

//    @Test
//    void testGetOrder() {
//        Integer orderId = 10;
//        Integer userId = 1;
//        Order o1 = new Order();
//        o1.setId(orderId);
//        o1.setUserId(userId);
//
//        doReturn(Optional.of(o1)).when(orderRepository).findById(orderId);
//        Order result = orderService.getOrder(orderId);
//
//        assertNotNull(result, "Order was not found");
//
//        assertEquals(orderId, result.getId(), "Wrong size of retrieved Orders list");
//        verify(orderRepository, times(1)).findById(orderId);
//    }
}
