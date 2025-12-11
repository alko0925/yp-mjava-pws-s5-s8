package ru.yp.sprint5pw.config;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.postgresql.PostgreSQLContainer;

public final class PostgreSQLTestcontainer {

    @Container
    @ServiceConnection
    static final PostgreSQLContainer postgreSQLContainer =
        new PostgreSQLContainer("postgres");
}
