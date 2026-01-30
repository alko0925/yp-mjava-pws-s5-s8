package ru.yp.sprint7pw;

import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.yp.sprint7pw.config.PostgreSQLTestcontainer;
import ru.yp.sprint7pw.config.RedisTestcontainer;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ImportTestcontainers({PostgreSQLTestcontainer.class, RedisTestcontainer.class})
@AutoConfigureWebTestClient
public class MartApplicationTest {
}
