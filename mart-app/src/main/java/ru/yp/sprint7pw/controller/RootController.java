package ru.yp.sprint7pw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.client.PaymentServiceClient;
import ru.yp.sprint7pw.client.domain.BalanceResponse;
import ru.yp.sprint7pw.client.domain.ErrorResponse;
import ru.yp.sprint7pw.controller.dto.BalanceDto;
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

    @GetMapping(value = "/balance/check")
    public Mono<Rendering> getBalance(@RequestParam(defaultValue = "") String topUpStatus) {

        StringBuilder balanceErrorMsg = new StringBuilder();
        return paymentServiceClient.getBalance(ControllerConstants.DEFAULT_USER_ID)
                .onErrorResume(error -> {
                    balanceErrorMsg.append("Error during attempt to get current balance.");
                    if (error instanceof WebClientRequestException wc_req_ex) {
                        balanceErrorMsg.append(" Details: ").append(wc_req_ex.getMessage());
                    } else if (error instanceof WebClientResponseException wc_res_ex) {
                        try {
                            ErrorResponse errorResponse = wc_res_ex.getResponseBodyAs(ErrorResponse.class);
                            if (errorResponse != null)
                                balanceErrorMsg.append(" Details: ").append(errorResponse.getMessage());
                        } catch (Exception ignored) {
                        }
                    }
                    return Mono.just(new BalanceResponse().balance(-1D));
                })
                .map(br -> {
                    return Rendering.view("check-balance")
                            .modelAttribute("balance", new BalanceDto(br.getBalance(), 0D))
                            .modelAttribute("topUpStatus", topUpStatus)
                            .modelAttribute("balanceErrorMsg", balanceErrorMsg.toString())
                            .build();
                });
    }

    @PostMapping(value = "/balance/topup")
    public Mono<String> topUpBalance(@ModelAttribute("balance") BalanceDto balance) {

        StringBuilder topUpStatus = new StringBuilder();
        StringBuilder redirectPath = new StringBuilder();

        return paymentServiceClient.updateBalance(ControllerConstants.DEFAULT_USER_ID, ControllerConstants.OperationType.DEPOSIT.toString(), balance.getTopUpValue())
                .onErrorResume(error -> {
                    topUpStatus.append("Top up is not possible at this moment.");
                    if (error instanceof WebClientRequestException wc_req_ex) {
                        topUpStatus.append(" Details: ").append(wc_req_ex.getMessage());
                    } else if (error instanceof WebClientResponseException wc_res_ex) {
                        try {
                            ErrorResponse errorResponse = wc_res_ex.getResponseBodyAs(ErrorResponse.class);
                            if (errorResponse != null)
                                topUpStatus.append(" Details: ").append(errorResponse.getMessage());
                        } catch (Exception ignored) {
                        }
                    }
                    return Mono.just(new BalanceResponse().balance(-1D));
                })
                .map(br -> {
                    if (topUpStatus.toString().isEmpty()) topUpStatus.append("Success");
                    return redirectPath.append("redirect:/balance/check?topUpStatus=").append(topUpStatus.toString()).toString().replace(" ", "%20");
                });
    }
}
