package ru.yp.sprint6pw.integration.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.yp.sprint6pw.PayApplicationTest;

public class PayApplicationWebTest extends PayApplicationTest {
    @Autowired
    protected WebTestClient webTestClient;

    @BeforeEach
    void setup() {
    }

    @AfterEach
    void cleanUp() {
    }
}
