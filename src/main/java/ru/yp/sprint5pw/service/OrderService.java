package ru.yp.sprint5pw.service;

import ru.yp.sprint5pw.model.Order;

public interface OrderService {
    Iterable<Order> getAllOrders();
    Order getOrder(Integer orderId);
}
