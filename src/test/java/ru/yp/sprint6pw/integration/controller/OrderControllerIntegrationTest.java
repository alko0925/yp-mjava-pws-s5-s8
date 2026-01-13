package ru.yp.sprint6pw.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class OrderControllerIntegrationTest extends MyMarketApplicationWebTest {
    @Test
    void getOrders_returnsViewOrders() throws Exception {
        webTestClient.get()
                .uri("/orders")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .value(html -> {
                    assert html.contains("<li class=\"list-group-item\">Prod1 (5 шт.) 5000 руб.</li>");
                    assert html.contains("<li class=\"list-group-item\">Prod3 (10 шт.) 30000 руб.</li>");
                    assert html.contains("<b>Сумма: 35000 руб.</b>");
                });
    }

    @Test
    void getOrder_returnsRedirectToViewOrder() throws Exception {
        Integer orderId = orderRepository.findAll()
                .collectList()
                .block()
                .getFirst()
                .getId();

        webTestClient.get()
                .uri("/orders/" + orderId)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .value(html -> {
                    assert html.contains("<b>Prod1</b>");
                    assert html.contains("<li class=\"list-group-item\">5 шт.</li>");
                    assert html.contains("<li class=\"list-group-item\">1000 руб.</li>");
                    assert html.contains("<b>Сумма: 5000 руб.</b>");
                    assert html.contains("<b>Prod3</b>");
                    assert html.contains("<li class=\"list-group-item\">10 шт.</li>");
                    assert html.contains("<li class=\"list-group-item\">3000 руб.</li>");
                    assert html.contains("<b>Сумма: 30000 руб.</b>");
                    assert html.contains("<h3>Сумма: 35000 руб.</h3>");
                });
    }
}
