package ru.yp.sprint7pw;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.yp.sprint7pw.config.PostgreSQLTestcontainer;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ImportTestcontainers(PostgreSQLTestcontainer.class)
@AutoConfigureWebTestClient
public class PayApplicationTest {

    @MockitoBean
    private CommandLineRunner commandLineRunner;
}
