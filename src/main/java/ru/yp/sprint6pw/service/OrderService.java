package ru.yp.sprint6pw.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yp.sprint6pw.model.Cart;
import ru.yp.sprint6pw.model.Order;
import ru.yp.sprint6pw.model.OrderProduct;

import java.util.List;

public interface OrderService {
    Mono<List<Order>> getAllOrders();
    Flux<Order> getOrderProductsForOrder(Order order);
    Mono<OrderProduct> setProductToOrderProduct(OrderProduct orderProduct);
//    Order getOrder(Integer orderId);
//    Order create(Cart cart);
}
