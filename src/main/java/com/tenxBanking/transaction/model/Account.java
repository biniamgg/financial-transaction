package com.tenxBanking.transaction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "account", schema = "test_schema")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class AccountBuilder{
        private BigDecimal balance;
        private String currency;
        private LocalDateTime createdAt;

        public AccountBuilder withBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public AccountBuilder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public AccountBuilder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Account build(){
            Account account = new Account();
            account.setBalance(balance);
            account.setCurrency(currency);
            account.setCreatedAt(createdAt);

            return account;
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
