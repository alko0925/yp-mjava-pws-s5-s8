package ru.yp.sprint7pw.controller.dto;

public class BalanceDto {
    private Double currentBalance;
    private Double topUpValue;

    public BalanceDto () {
    }

    public BalanceDto(Double currentBalance, Double topUpValue) {
        this.currentBalance = currentBalance;
        this.topUpValue = topUpValue;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Double getTopUpValue() {
        return topUpValue;
    }

    public void setTopUpValue(Double topUpValue) {
        this.topUpValue = topUpValue;
    }
}
