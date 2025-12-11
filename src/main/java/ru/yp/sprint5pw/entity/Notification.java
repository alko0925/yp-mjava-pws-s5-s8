package ru.yp.sprint5pw.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Идентификатор, генерируется на уровне БД
    private String message; // Сообщение в уведомлении

    // No-args конструктор
    public Notification() {}

    // Конструктор для инициализации объекта по полю message
    public Notification(String message) {
        this.message = message;
    }

    // Геттеры-сеттеры
    public Integer getId() {
        return id;
    }

    public Notification setId(Integer id) {
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
