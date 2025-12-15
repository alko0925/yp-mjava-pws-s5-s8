package ru.yp.sprint5pw;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.yp.sprint5pw.config.PostgreSQLTestcontainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Testcontainers
@ImportTestcontainers(PostgreSQLTestcontainer.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MyMarketApplicationTest {
}
