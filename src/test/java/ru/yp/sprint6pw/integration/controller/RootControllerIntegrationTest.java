package ru.yp.sprint6pw.integration.controller;

import org.junit.jupiter.api.Test;

public class RootControllerIntegrationTest extends MyMarketApplicationWebTest {

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

        webTestClient.post()
                .uri("/buy")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/orders/" + orderId + "?newOrder=true");
    }
}
