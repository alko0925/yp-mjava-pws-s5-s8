package ru.yp.sprint5pw.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import ru.yp.sprint5pw.entity.Notification;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

@Service
public class NotificationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public NotificationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    }

    // Метод для батчевой вставки с использованием JdbcTemplate
    public void saveNotificationsWithJdbcTemplate(List<Notification> notifications) {
        // При сохранении не учитываем идентификаторы, их проставляет БД, сохраняем только:
        validateEntitiesHaveNoIds(notifications);
        jdbcTemplate.batchUpdate(
                "INSERT INTO notification (message) VALUES (?)",
                notifications,
                notifications.size(),
                // Маппер для проставления полей Notification в аргументы PreparedStatement
                (PreparedStatement ps, Notification notification) ->
                        ps.setString(1, notification.getMessage())
        );
    }

    // Метод для батчевой вставки с использованием SimpleJdbcInsert
    public void saveNotificationsWithSimpleJdbcInsert(List<Notification> notifications) {
        validateEntitiesHaveNoIds(notifications);
        var batchArguments = notifications.stream()
                .map(
                        it -> Map.of("message", it.getMessage())
                ).toList()
                .toArray(it -> new Map[0]);
        simpleJdbcInsert.withTableName("notification")
                .usingColumns("message")
                .executeBatch(batchArguments);
    }

    private static void validateEntitiesHaveNoIds(List<Notification> notifications) {
        if (notifications.stream().anyMatch(it -> it.getId() != null)) {
            throw new IllegalArgumentException(
                    "Идентификаторы нотификаций проставляются на уровне БД, самостоятельное назначение не предусмотрено"
            );
        }
    }
}
