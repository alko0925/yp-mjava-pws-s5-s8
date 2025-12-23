package ru.yp.sprint5pw.service;

import ru.yp.sprint5pw.model.Cart;
import ru.yp.sprint5pw.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrder(Integer orderId);
    Order create(Cart cart);
}
