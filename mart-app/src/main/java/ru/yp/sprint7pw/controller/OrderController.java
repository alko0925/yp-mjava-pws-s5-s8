package ru.yp.sprint7pw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.controller.dto.ItemDto;
import ru.yp.sprint7pw.controller.dto.OrderDto;
import ru.yp.sprint7pw.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Mono<Rendering> getOrders() {

        return orderService.getAllOrders()
                .map(orders -> {
                    List<OrderDto> ordersDto = orders.stream().map(o -> {
                        OrderDto orderDto = new OrderDto();
                        orderDto.setId(o.getId());
                        orderDto.setItems(o.getOrderProducts().stream().map(op -> {
                            ItemDto item = new ItemDto();
                            item.setId(op.getProduct().getId());
                            item.setTitle(op.getProduct().getTitle());
                            item.setDescription(op.getProduct().getDescription());
                            item.setImgPath(op.getProduct().getImgPath());
                            item.setPrice(op.getProduct().getPrice());
                            item.setCount(op.getQuantity());
                            return item;
                        }).toList());
                        orderDto.setTotalSum(o.getTotalOrderPrice());
                        return orderDto;
                    }).toList();

                    return Rendering.view("orders")
                            .modelAttribute("orders", ordersDto)
                            .build();
                });
    }

    @GetMapping(value = "/{order_id}")
    public Mono<Rendering> getOrder(@PathVariable("order_id") Integer orderId,
                                    @RequestParam(defaultValue = "false") Boolean newOrder) {

        return orderService.getOrder(orderId)
                .map(o -> {
                    OrderDto orderDto = new OrderDto();
                    orderDto.setId(o.getId());
                    orderDto.setItems(o.getOrderProducts().stream().map(op -> {
                        ItemDto item = new ItemDto();
                        item.setId(op.getProduct().getId());
                        item.setTitle(op.getProduct().getTitle());
                        item.setDescription(op.getProduct().getDescription());
                        item.setImgPath(op.getProduct().getImgPath());
                        item.setPrice(op.getProduct().getPrice());
                        item.setCount(op.getQuantity());
                        return item;
                    }).toList());
                    orderDto.setTotalSum(o.getTotalOrderPrice());

                    return Rendering.view("order")
                            .modelAttribute("order", orderDto)
                            .modelAttribute("newOrder", newOrder)
                            .build();
                });
    }
}
