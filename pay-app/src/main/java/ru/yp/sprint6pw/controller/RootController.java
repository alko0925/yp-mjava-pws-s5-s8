package ru.yp.sprint6pw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
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

    @GetMapping
    public Mono<String> getItems(@RequestParam(defaultValue = "") String search,
                                 @RequestParam(defaultValue = "NO") String sort,
                                 @RequestParam(defaultValue = "1") Integer pageNumber,
                                 @RequestParam(defaultValue = "5") Integer pageSize) {

        return Mono.just("redirect:/items?search=" +
                search +
                "&sort=" +
                sort +
                "&pageNumber=" +
                pageNumber +
                "&pageSize=" +
                pageSize);
    }

    @PostMapping(value = "/buy")
    public Mono<String> submitOrder() {
        return cartService.getCartByUserId(1)
                .flatMap(cart -> {
                    if (cart.getId() != null) return orderService.create(cart);
                    else return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new NoSuchElementException("Cart not found")))
                .zipWith(cartService.getCartByUserId(1)
                        .flatMap(cartService::delete)
                        .thenReturn(""))
                .map(tuple -> "redirect:/orders/" +
                        tuple.getT1().getId() +
                        "?newOrder=true");
    }
}
