package ru.yp.sprint5pw.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.yp.sprint5pw.model.Cart;
import ru.yp.sprint5pw.model.CartProduct;
import ru.yp.sprint5pw.model.Order;
import ru.yp.sprint5pw.model.OrderProduct;
import ru.yp.sprint5pw.repository.CartProductRepository;
import ru.yp.sprint5pw.repository.OrderProductRepository;
import ru.yp.sprint5pw.repository.OrderRepository;
import ru.yp.sprint5pw.repository.ProductRepository;

import java.util.NoSuchElementException;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(Integer orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new NoSuchElementException("Order not found"));
    }
}
