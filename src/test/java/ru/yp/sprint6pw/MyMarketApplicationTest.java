package ru.yp.sprint6pw;

import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.yp.sprint6pw.config.PostgreSQLTestcontainer;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ImportTestcontainers(PostgreSQLTestcontainer.class)
@AutoConfigureWebTestClient
public class MyMarketApplicationTest {
}
