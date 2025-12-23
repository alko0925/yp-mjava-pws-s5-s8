package ru.yp.sprint6pw.service;

import ru.yp.sprint6pw.model.Cart;
import ru.yp.sprint6pw.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrder(Integer orderId);
    Order create(Cart cart);
}
