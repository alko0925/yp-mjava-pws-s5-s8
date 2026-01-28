package ru.yp.sprint7pw.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.service.AccountService;

@RestController
public class AccountController implements AccountApi {

    private final AccountService accountService;
    private final int DEFAULT_USER_ID = 1;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Mono<ResponseEntity<Double>> getBalance(Integer userId, ServerWebExchange exchange) {
        return accountService.getBalanceByUserId(userId)
                .map(balance -> ResponseEntity.ok().body(balance));
    }

    @Override
    public Mono<ResponseEntity<Double>> updateBalance(Integer userId, String operation, Double amount, ServerWebExchange exchange) {
        return accountService.updateBalanceByUserId(userId, operation, amount)
                .map(balance -> ResponseEntity.ok().body(balance));
    }
}
