package com.tenxBanking.transaction.dto;

import java.math.BigDecimal;

public class TransferDtoBuilder {
    private Long sourceAccount;
    private Long targetAccount;
    private BigDecimal amount;
    private String currency;

    public TransferDtoBuilder withSourceAccount(Long sourceAccount) {
        this.sourceAccount = sourceAccount;
        return this;
    }

    public TransferDtoBuilder withTargetAccount(Long targetAccount) {
        this.targetAccount = targetAccount;
        return this;
    }

    public TransferDtoBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public TransferDtoBuilder withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public TransferDto build() {
        TransferDto transferDto = new TransferDto();
        transferDto.setSourceAccount(sourceAccount);
        transferDto.setTargetAccount(targetAccount);
        transferDto.setAmount(amount);
        transferDto.setCurrency(currency);

        return transferDto;
    }
}