package ru.yp.sprint7pw.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Table(schema = "pay_app", name = "account_operations")
public class AccountOperation {

    @Id
    private BigInteger id;

    private Integer accountId;

    private String operation;

    private Double amount;

    private Double originalBalance;

    private Double newBalance;

    private LocalDateTime executedAt;

    public AccountOperation() {
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getOriginalBalance() {
        return originalBalance;
    }

    public void setOriginalBalance(Double originalBalance) {
        this.originalBalance = originalBalance;
    }

    public Double getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(Double newBalance) {
        this.newBalance = newBalance;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }
}
