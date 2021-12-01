package com.tenxBanking.transaction.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferDto {
    private Long sourceAccount;
    private Long targetAccount;
    private BigDecimal amount;
    private String currency;

    public TransferDto() { }

    public Long getSourceAccount() {
        return sourceAccount;
    }

    public Long getTargetAccount() {
        return targetAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setSourceAccount(Long sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public void setTargetAccount(Long targetAccount) {
        this.targetAccount = targetAccount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
