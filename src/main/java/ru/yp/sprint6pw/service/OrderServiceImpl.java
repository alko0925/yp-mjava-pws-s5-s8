package ru.yp.sprint6pw.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
    private final ProductService productService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderProductRepository orderProductRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.productService = productService;
    }

    @Override
    public Mono<List<Order>> getAllOrders() {
        return orderRepository.findAll()
                .flatMap(this::getOrderProductsForOrder).collectList();
    }

    @Override
    public Flux<Order> getOrderProductsForOrder(Order order) {
        return Flux.from(orderProductRepository.findAllById(List.of(order.getId()))
                .flatMap(this::setProductToOrderProduct).collectList()
                .map(ops -> {
                    order.setOrderProducts(ops);
                    return order;
                }));
    }

    @Override
    public Mono<OrderProduct> setProductToOrderProduct(OrderProduct orderProduct) {
        return productService.getProduct(orderProduct.getProductId())
                .map(p -> {
                    orderProduct.setProduct(p);
                    return orderProduct;
                });
    }

    @Override
    public Mono<Order> getOrder(Integer orderId) {
        return orderRepository.findById(orderId)
                .flatMapMany(this::getOrderProductsForOrder)
                .next()
                .switchIfEmpty(Mono.error(new NoSuchElementException("Order not found")));
    }

    @Override
    public Mono<Order> create(Cart cart) {
        Order order = new Order();
        order.setUserId(cart.getUserId());
        return orderRepository.save(order)
                .flatMap(o -> saveOrderProductsForOrder(order, cart));
    }

    @Override
    public Mono<Order> saveOrderProductsForOrder(Order order, Cart cart) {
        return Flux.fromStream(cart.getCartProducts().stream())
                .flatMap(cp -> orderProductRepository.saveByOrderIdAndProductId(order.getId(), cp.getProductId(), cp.getQuantity())).collectList()
                .thenReturn(order);
    }
}
