package ru.yp.sprint7pw.service;

import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.model.Account;

public interface AccountService {
    Mono<Double> getBalanceByUserId(Integer userId);
    Mono<Account> getAccountByUserId(Integer userId);
    Mono<Double> updateBalanceByUserId(Integer userId, String operation, Double amount);
    Mono<Account> applyOperationToAccount(Account account, String operation, Double amount);
}
