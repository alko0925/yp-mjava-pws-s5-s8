package ru.yp.sprint7pw.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.MartApplicationTest;
import ru.yp.sprint7pw.model.Order;
import ru.yp.sprint7pw.model.OrderProduct;
import ru.yp.sprint7pw.model.Product;
import ru.yp.sprint7pw.repository.OrderProductRepository;
import ru.yp.sprint7pw.repository.OrderRepository;
import ru.yp.sprint7pw.service.OrderService;
import ru.yp.sprint7pw.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class OrderServiceTest extends MartApplicationTest {

    @MockitoBean
    private OrderRepository orderRepository;

    @MockitoBean
    private OrderProductRepository orderProductRepository;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    void resetMocks() {
        reset(orderRepository, orderProductRepository);
    }

    @Test
    void testGetAllOrders() {
        //Ids
        Integer userId = 1;

        Integer o1_id = 1;
        Integer o2_id = 2;

        Integer p1_id = 1;
        Integer p2_id = 2;
        Integer p3_id = 3;

        Integer p1_quantity = 5;
        Integer p2_quantity = 10;
        Integer p3_quantity = 15;

        //Orders
        List<Order> orders = new ArrayList<>();
        Order o1 = new Order();
        o1.setId(o1_id);
        o1.setUserId(userId);
        orders.add(o1);
        Order o2 = new Order();
        o2.setId(o2_id);
        o2.setUserId(userId);
        orders.add(o2);

        //Products
        Product p1 = new Product();
        p1.setId(p1_id);
        Product p2 = new Product();
        p2.setId(p2_id);
        Product p3 = new Product();
        p3.setId(p3_id);

        //OrderProducts for Order #1
        List<OrderProduct> o1_orderProducts = new ArrayList<>();
        OrderProduct op1 = new OrderProduct();
        op1.setOrderId(o1_id);
        op1.setProductId(p1_id);
        op1.setQuantity(p1_quantity);
        o1_orderProducts.add(op1);

        OrderProduct op2 = new OrderProduct();
        op2.setOrderId(o1_id);
        op2.setProductId(p2_id);
        op2.setQuantity(p2_quantity);
        o1_orderProducts.add(op2);

        //OrderProducts for Order #2
        List<OrderProduct> o2_orderProducts = new ArrayList<>();
        OrderProduct op3 = new OrderProduct();
        op3.setOrderId(o2_id);
        op3.setProductId(p3_id);
        op3.setQuantity(p3_quantity);
        o2_orderProducts.add(op3);

        doReturn(Flux.fromIterable(orders)).when(orderRepository).findAll();
        doReturn(Flux.fromIterable(o1_orderProducts)).when(orderProductRepository).findAllById(List.of(o1.getId()));
        doReturn(Flux.fromIterable(o2_orderProducts)).when(orderProductRepository).findAllById(List.of(o2.getId()));
        doReturn(Mono.just(p1)).when(productService).getProduct(op1.getProductId());
        doReturn(Mono.just(p2)).when(productService).getProduct(op2.getProductId());
        doReturn(Mono.just(p3)).when(productService).getProduct(op3.getProductId());

        List<Order> result = orderService.getAllOrders().block();

        assertNotNull(result, "List of Orders should not be null");
        assertEquals(orders.size(), result.size(), "Wrong size of retrieved Orders list");
        assertEquals(userId, result.get(0).getUserId(), "Wrong Order was retrieved for specific User");
        assertEquals(userId, result.get(1).getUserId(), "Wrong Order was retrieved for specific User");

        verify(orderRepository, times(1)).findAll();
        verify(orderProductRepository, times(1)).findAllById(List.of(o1.getId()));
        verify(orderProductRepository, times(1)).findAllById(List.of(o2.getId()));
        verify(productService, times(1)).getProduct(op1.getProductId());
        verify(productService, times(1)).getProduct(op2.getProductId());
        verify(productService, times(1)).getProduct(op3.getProductId());
    }

    @Test
    void testGetOrder() {
        //Ids
        Integer userId = 1;
        Integer orderId = 1;

        Integer p1_id = 1;
        Integer p2_id = 2;

        Integer p1_quantity = 5;
        Integer p2_quantity = 10;

        //Order
        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);

        //Products
        Product p1 = new Product();
        p1.setId(p1_id);
        Product p2 = new Product();
        p2.setId(p2_id);

        //OrderProducts for Order #1
        List<OrderProduct> o1_orderProducts = new ArrayList<>();
        OrderProduct op1 = new OrderProduct();
        op1.setOrderId(orderId);
        op1.setProductId(p1_id);
        op1.setQuantity(p1_quantity);
        o1_orderProducts.add(op1);

        OrderProduct op2 = new OrderProduct();
        op2.setOrderId(orderId);
        op2.setProductId(p2_id);
        op2.setQuantity(p2_quantity);
        o1_orderProducts.add(op2);

        doReturn(Mono.just(order)).when(orderRepository).findById(orderId);
        doReturn(Flux.fromIterable(o1_orderProducts)).when(orderProductRepository).findAllById(List.of(order.getId()));
        doReturn(Mono.just(p1)).when(productService).getProduct(op1.getProductId());
        doReturn(Mono.just(p2)).when(productService).getProduct(op2.getProductId());

        Order result = orderService.getOrder(orderId).block();

        assertNotNull(result, "Order should not be null");
        assertEquals(userId, result.getUserId(), "Wrong Order was retrieved for specific User");
        assertEquals(2, result.getOrderProducts().size(), "Order was retrieved with from Product count");

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderProductRepository, times(1)).findAllById(List.of(order.getId()));
        verify(productService, times(1)).getProduct(op1.getProductId());
        verify(productService, times(1)).getProduct(op2.getProductId());
    }
}
