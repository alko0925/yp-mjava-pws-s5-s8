package ru.yp.sprint7pw.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "pay_app", name = "accounts")
public class Account {

    @Id
    private Integer id;

    private Integer userId;

    private Double balance;

    public Account() {
    }

    public Account(Integer id, Integer userId, Double balance) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
    }

    public Account(Integer userId, Double balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
