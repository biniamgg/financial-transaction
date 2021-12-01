package com.tenxBanking.transaction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction", schema = "test_schema")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @JsonProperty("transactionId")
    private long id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false)
    private String currency;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "source_account_id", nullable = false)
    private Account sourceAccount;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "target_account_id", nullable = false)
    private Account targetAccount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(Account targetAccount) {
        this.targetAccount = targetAccount;
    }

    public static class TransactionBuilder{
        private BigDecimal amount;
        private String currency;
        private Account sourceAccount;
        private Account targetAccount;

        public TransactionBuilder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public TransactionBuilder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public TransactionBuilder withSourceAccount(Account sourceAccount) {
            this.sourceAccount = sourceAccount;
            return this;
        }

        public TransactionBuilder withTargetAccount(Account targetAccount) {
            this.targetAccount = targetAccount;
            return this;
        }

        public Transaction build(){
            Transaction transaction = new Transaction();
            transaction.setSourceAccount(sourceAccount);
            transaction.setTargetAccount(targetAccount);
            transaction.setAmount(amount);
            transaction.setCurrency(currency);

            return transaction;
        }
    }
}
