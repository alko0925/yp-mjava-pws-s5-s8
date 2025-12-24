package ru.yp.sprint6pw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yp.sprint6pw.model.Cart;
import ru.yp.sprint6pw.model.Order;
import ru.yp.sprint6pw.service.CartService;
import ru.yp.sprint6pw.service.OrderService;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/")
public class RootController {

    private final OrderService orderService;
    private final CartService cartService;

    public RootController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

//    @GetMapping
//    public String getItems(@RequestParam(defaultValue = "") String search,
//                           @RequestParam(defaultValue = "NO") String sort,
//                           @RequestParam(defaultValue = "1") Integer pageNumber,
//                           @RequestParam(defaultValue = "5") Integer pageSize) {
//
//        return "redirect:/items?search=" +
//                search +
//                "&sort=" +
//                sort +
//                "&pageNumber=" +
//                pageNumber +
//                "&pageSize=" +
//                pageSize;
//    }

//    @PostMapping(value = "/buy")
//    public String submitOrder() {
//        Cart cart = cartService.getCartByUserId(1);
//        if (cart == null) throw new NoSuchElementException("Cart not found");
//        Order order = orderService.create(cart);
//        cartService.delete(cart);
//        return "redirect:/orders/" +
//                order.getId() +
//                "?newOrder=true";
//    }
}
