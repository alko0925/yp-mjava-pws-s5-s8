package ru.yp.sprint7pw.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class AccountControllerIntegrationTest extends PayApplicationWebTest {

    @Test
    void getBalance_returnsCurrentUserBalance() throws Exception {
        Integer userId = userRepository.findAll()
                .collectList()
                .block()
                .getFirst()
                .getId();

        webTestClient.get()
                .uri("/api/accounts/" + userId)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.balance").isEqualTo(10000);
    }

    @Test
    void updateBalance_returnsUpdatedUserBalance() throws Exception {
        Integer userId = userRepository.findAll()
                .collectList()
                .block()
                .getFirst()
                .getId();

        webTestClient.put()
                .uri("/api/accounts/" + userId + "?operation=deposit&amount=1000")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.balance").isEqualTo(11000);
    }
}
