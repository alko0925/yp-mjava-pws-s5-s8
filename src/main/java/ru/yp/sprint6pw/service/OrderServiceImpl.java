package ru.yp.sprint6pw.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.yp.sprint6pw.model.Cart;
import ru.yp.sprint6pw.model.Order;
import ru.yp.sprint6pw.model.OrderProduct;
import ru.yp.sprint6pw.repository.OrderProductRepository;
import ru.yp.sprint6pw.repository.OrderRepository;

import java.util.List;
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

//    @Override
//    public List<Order> getAllOrders() {
//        return orderRepository.findAll();
//    }

//    @Override
//    public Order getOrder(Integer orderId) {
//        return orderRepository.findById(orderId).orElseThrow(() -> new NoSuchElementException("Order not found"));
//    }

//    @Override
//    public Order create(Cart cart) {
//        Order order = new Order();
//        order.setUserId(cart.getUserId());
//        orderRepository.save(order);
//
//        for(var cp : cart.getCartProducts()){
//            OrderProduct orderProduct = new OrderProduct(order, cp.getProduct(), cp.getQuantity());
//            orderProductRepository.save(orderProduct);
//            order.getOrderProducts().add(orderProduct);
//        }
//
//        return orderRepository.save(order);
//    }
}
