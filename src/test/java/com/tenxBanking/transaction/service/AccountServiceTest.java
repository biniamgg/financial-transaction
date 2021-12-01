package com.tenxBanking.transaction.service;

import com.tenxBanking.transaction.exception.AccountNotFoundException;
import com.tenxBanking.transaction.model.Account;
import com.tenxBanking.transaction.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {

  private AccountRepository repository;

  private AccountService service;

  @BeforeEach
  void setUp() {
    repository = mock(AccountRepository.class);
    service = new AccountService(repository);
  }

  @DisplayName("Given account found, return account.")
  @Test
  void getAccountTest_happyPath() {
    Account account = new Account.AccountBuilder()
            .withBalance(BigDecimal.valueOf(100d))
            .withCurrency("GBP")
            .withCreatedAt(LocalDateTime.now())
            .build();

    when(repository.findById(anyLong())).thenReturn(Optional.of(account));
    assertEquals(account, service.getAccountById(10L));
  }

  @DisplayName("Given account cannot be found, AccountNotFoundException is thrown.")
  @Test
  void getAccountTest_whenNotFound() {
    when(repository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(AccountNotFoundException.class, () -> service.getAccountById(10L));
  }
}
