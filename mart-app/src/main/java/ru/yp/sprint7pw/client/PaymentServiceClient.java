package ru.yp.sprint7pw.client;

import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.client.domain.BalanceResponse;

public interface PaymentServiceClient {
    Mono<BalanceResponse> getBalance(Integer userId);
    Mono<BalanceResponse> updateBalance(Integer userId, String operation, Double amount);
}
