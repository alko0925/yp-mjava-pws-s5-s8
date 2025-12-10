package ru.yp.sprint5pw.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yp.sprint5pw.MyMarketApplicationTest;
import ru.yp.sprint5pw.entity.Notification;
import ru.yp.sprint5pw.repository.NotificationRepository;
import ru.yp.sprint5pw.service.NotificationDao;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class BatchOperationsTest extends MyMarketApplicationTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationDao notificationDao;

    private final int batchSize = 100;

    @BeforeEach
    public void setUp() {
        notificationRepository.deleteAll();
    }

    @Test
    public void testBatchOperationsInRepository() {
        // Батчевое сохранение с использованием репозитория
        notificationRepository.saveAll(generateNotifications(batchSize));

        assertThat(notificationRepository.count())
                .withFailMessage("Количество найденных сущностей должно совпадать с числом сохранённых в батче")
                .isEqualTo(batchSize);
    }

    @Test
    public void testBatchOperationsInJdbcTemplate() {
        // Батчевое сохранение с использованием JdbcTemplate
        notificationDao.saveNotificationsWithJdbcTemplate(generateNotifications(batchSize));

        assertThat(notificationRepository.count())
                .withFailMessage("Количество найденных сущностей должно совпадать с числом сохранённых в батче")
                .isEqualTo(batchSize);
    }

    @Test
    public void testBatchOperationsInSimpleJdbcInsert() {
        // Батчевое сохранение с использованием JdbcTemplate
        notificationDao.saveNotificationsWithSimpleJdbcInsert(generateNotifications(batchSize));

        assertThat(notificationRepository.count())
                .withFailMessage("Количество найденных сущностей должно совпадать с числом сохранённых в батче")
                .isEqualTo(batchSize);
    }

    private List<Notification> generateNotifications(int count) {
        return IntStream.range(1, count + 1)
                .mapToObj(i -> new Notification("Notification " + i))
                .toList();
    }
}
