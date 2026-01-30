package ru.yp.sprint7pw.integration.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.client.PaymentServiceClient;
import ru.yp.sprint7pw.client.domain.BalanceResponse;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;

public class RootControllerIntegrationTest extends MartApplicationWebTest {

    @MockitoBean
    private PaymentServiceClient paymentServiceClient;

    @BeforeEach
    void resetMocks() {
        reset(paymentServiceClient);
    }

    @Test
    void getItems_returnsRedirectionToItems() throws Exception {
        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/items?search=&sort=NO&pageNumber=1&pageSize=5");
    }

    @Test
    void submitOrder_returnsRedirectionToOrder() throws Exception {
        Integer orderId = orderRepository.findAll()
                .collectList()
                .block()
                .getFirst()
                .getId() + 1;

        doReturn(Mono.just(new BalanceResponse().balance(20000D))).when(paymentServiceClient).updateBalance(Mockito.any(), Mockito.any(), Mockito.any());

        webTestClient.post()
                .uri("/buy")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/orders/" + orderId + "?newOrder=true");
    }
}
