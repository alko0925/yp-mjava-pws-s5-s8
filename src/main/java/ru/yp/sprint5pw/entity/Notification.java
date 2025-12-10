package ru.yp.sprint5pw.entity;

import org.springframework.data.annotation.Id;

public class Notification {

    @Id
    private Long id; // Идентификатор, генерируется на уровне БД
    private String message; // Сообщение в уведомлении

    // No-args конструктор
    public Notification() {}

    // Конструктор для инициализации объекта по полю message
    public Notification(String message) {
        this.message = message;
    }

    // Геттеры-сеттеры
    public Long getId() {
        return id;
    }

    public Notification setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Notification setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
