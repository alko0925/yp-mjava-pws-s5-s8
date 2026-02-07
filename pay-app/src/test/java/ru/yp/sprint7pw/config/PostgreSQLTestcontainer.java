package ru.yp.sprint7pw.config;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public final class PostgreSQLTestcontainer {

    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> postgreSQLContainer =
        new PostgreSQLContainer<>("postgres");
}
