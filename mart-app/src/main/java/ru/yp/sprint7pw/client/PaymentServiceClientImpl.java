package ru.yp.sprint7pw.client;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.client.api.AccountApi;
import ru.yp.sprint7pw.client.domain.BalanceResponse;

@Service
public class PaymentServiceClientImpl implements PaymentServiceClient {
    private final AccountApi accountApi;

    public PaymentServiceClientImpl(AccountApi accountApi) {
        this.accountApi = accountApi;
    }

    @Override
    public Mono<BalanceResponse> getBalance(Integer userId) {
        return accountApi.getBalance(userId);
    }

    @Override
    public Mono<BalanceResponse> updateBalance(Integer userId, String operation, Double amount) {
        return accountApi.updateBalance(userId, operation, amount);
    }
}
