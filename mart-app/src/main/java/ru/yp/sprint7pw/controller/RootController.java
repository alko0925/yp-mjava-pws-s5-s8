package ru.yp.sprint7pw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.client.PaymentServiceClient;
import ru.yp.sprint7pw.client.domain.BalanceResponse;
import ru.yp.sprint7pw.client.domain.ErrorResponse;
import ru.yp.sprint7pw.model.Order;
import ru.yp.sprint7pw.service.CartService;
import ru.yp.sprint7pw.service.OrderService;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/")
public class RootController {

    private final OrderService orderService;
    private final CartService cartService;
    private final PaymentServiceClient paymentServiceClient;

    public RootController(OrderService orderService, CartService cartService, PaymentServiceClient paymentServiceClient) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.paymentServiceClient = paymentServiceClient;
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

        StringBuilder paymentErrorMsg = new StringBuilder();
        StringBuilder redirectPath = new StringBuilder();
        final Integer cartTotalPrice;

        return cartService.getCartByUserId(ControllerConstants.DEFAULT_USER_ID)
                .flatMap(cart -> {
                    if (cart.getId() != null) {
                        return paymentServiceClient.updateBalance(ControllerConstants.DEFAULT_USER_ID, ControllerConstants.OperationType.PAYMENT.toString(), cart.getTotalCartPrice().doubleValue())
                                .onErrorResume(error -> {
                                    paymentErrorMsg.append("Payment is not possible at this moment.");
                                    if (error instanceof WebClientRequestException wc_req_ex) {
                                        paymentErrorMsg.append(" Details: ").append(wc_req_ex.getMessage());
                                    } else if (error instanceof WebClientResponseException wc_res_ex) {
                                        try {
                                            ErrorResponse errorResponse = wc_res_ex.getResponseBodyAs(ErrorResponse.class);
                                            if (errorResponse != null)
                                                paymentErrorMsg.append(" Details: ").append(errorResponse.getMessage());
                                        } catch (Exception ignored) {
                                        }
                                    }
                                    return Mono.just(new BalanceResponse().balance(-1D));
                                });
                    } else return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new NoSuchElementException("Cart not found")))
                .then(cartService.getCartByUserId(ControllerConstants.DEFAULT_USER_ID)
                        .flatMap(cart -> {
                            if (paymentErrorMsg.toString().isEmpty()) return orderService.create(cart);
                            else return Mono.just(new Order());
                        }))
                .map(order -> {
                    if (paymentErrorMsg.toString().isEmpty())
                        redirectPath.append("redirect:/orders/").append(order.getId()).append("?newOrder=true");
                    else redirectPath.append("redirect:/cart/items?failedPayment=true");
                    return "";
                })
                .then(cartService.getCartByUserId(ControllerConstants.DEFAULT_USER_ID)
                        .flatMap(cart -> {
                            if (paymentErrorMsg.toString().isEmpty()) return cartService.delete(cart);
                            else return Mono.just("");
                        })
                        .thenReturn(""))
                .map(s -> s + redirectPath.toString());
    }
}
