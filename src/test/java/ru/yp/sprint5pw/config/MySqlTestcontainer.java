package ru.yp.sprint5pw.config;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

public final class MySqlTestcontainer {

    @Container
    @ServiceConnection
    static final MySQLContainer<?> mysqlContainer =
        new MySQLContainer<>("mysql:8.0.28");

}
