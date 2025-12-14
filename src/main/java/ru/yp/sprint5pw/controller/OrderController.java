package ru.yp.sprint5pw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yp.sprint5pw.controller.dto.ItemDto;
import ru.yp.sprint5pw.controller.dto.OrderDto;
import ru.yp.sprint5pw.model.Order;
import ru.yp.sprint5pw.service.CartService;
import ru.yp.sprint5pw.service.OrderService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping
    public String getOrders(Model model) {

        Iterable<Order> orders = orderService.getAllOrders();
        List<OrderDto> ordersDto = new ArrayList<>();

        for (var o : orders) {
            OrderDto orderDto = new OrderDto();
            orderDto.setId(o.getId());
            for (var op : o.getOrderProducts()) {
                ItemDto item = new ItemDto();
                item.setId(op.getProduct().getId());
                item.setTitle(op.getProduct().getTitle());
                item.setDescription(op.getProduct().getDescription());
                item.setImgPath(op.getProduct().getImgPath());
                item.setPrice(op.getProduct().getPrice());
                item.setCount(op.getQuantity());
                orderDto.getItems().add(item);
            }
            orderDto.setTotalSum(o.getTotalOrderPrice());
            ordersDto.add(orderDto);
        }

        model.addAttribute("orders", ordersDto);

        return "orders";
    }

    @GetMapping(value = "/{order_id}")
    public String getOrder(@PathVariable("order_id") Integer orderId,
                           @RequestParam(defaultValue = "false") Boolean newOrder,
                           Model model) {

        Order order = orderService.getOrder(orderId);
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        for (var op : order.getOrderProducts()) {
            ItemDto item = new ItemDto();
            item.setId(op.getProduct().getId());
            item.setTitle(op.getProduct().getTitle());
            item.setDescription(op.getProduct().getDescription());
            item.setImgPath(op.getProduct().getImgPath());
            item.setPrice(op.getProduct().getPrice());
            item.setCount(op.getQuantity());
            orderDto.getItems().add(item);
        }
        orderDto.setTotalSum(order.getTotalOrderPrice());

        model.addAttribute("order", orderDto);
        model.addAttribute("newOrder", newOrder);

        return "order";
    }
}
