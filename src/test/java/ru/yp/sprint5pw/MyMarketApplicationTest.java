package ru.yp.sprint5pw;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.yp.sprint5pw.config.PostgreSQLTestcontainer;

@SpringBootTest
@Testcontainers
@ImportTestcontainers(PostgreSQLTestcontainer.class)
@ActiveProfiles("test")
public class MyMarketApplicationTest {

    @Test
    void contextLoads() {
    }
}
