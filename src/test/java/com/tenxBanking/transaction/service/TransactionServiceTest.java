package com.tenxBanking.transaction.service;

import com.tenxBanking.transaction.dto.TransferDto;
import com.tenxBanking.transaction.dto.TransferDtoBuilder;
import com.tenxBanking.transaction.exception.AccountNotFoundException;
import com.tenxBanking.transaction.exception.InsufficientBalanceException;
import com.tenxBanking.transaction.exception.SameAccountException;
import com.tenxBanking.transaction.model.Account;
import com.tenxBanking.transaction.model.Transaction;
import com.tenxBanking.transaction.repository.TransactionRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransactionServiceTest {

    private TransferDto transferDto;

    private TransactionService service;

    private AccountService accountService;
    private TransactionRepository repository;

    private Account sourceAccount;
    private Account targetAccount;

    @BeforeEach
    void setUp() {

        accountService = mock(AccountService.class);
        repository = mock(TransactionRepository.class);

        service = new TransactionService(accountService, repository);

        transferDto = new TransferDtoBuilder()
                .withSourceAccount(1L)
                .withTargetAccount(2L)
                .withAmount(BigDecimal.valueOf(10.00d))
                .withCurrency("GBP")
                .build();

        sourceAccount = Mockito.spy(new Account.AccountBuilder()
                .withBalance(BigDecimal.valueOf(100d))
                .withCurrency("GBP")
                .withCreatedAt(LocalDateTime.now())
                .build());

        targetAccount = new Account.AccountBuilder()
                .withBalance(BigDecimal.valueOf(100d))
                .withCurrency("GBP")
                .withCreatedAt(LocalDateTime.now())
                .build();
    }

    @DisplayName("Given valid account with sufficient balance, then balance is transferred and reflected on the account balance.")
    @Test
    void transferMoney_happyPath(){
        when(accountService.getAccountById(eq(1L))).thenReturn(sourceAccount);
        when(accountService.getAccountById(eq(2L))).thenReturn(targetAccount);

        service.transferBalance(transferDto);

        verify(accountService, times(2)).getAccountById(anyLong());
        verify(repository, times(1)).save(any(Transaction.class));

        assertThat(BigDecimal.valueOf(90d),  Matchers.comparesEqualTo(sourceAccount.getBalance()));
        assertThat(BigDecimal.valueOf(110d),  Matchers.comparesEqualTo(targetAccount.getBalance()));

    }

    @DisplayName("Given valid account with insufficient balance, then balance is not transferred and InsufficientBalanceException is thrown.")
    @Test
    void transferMoney_withInsufficientBalance(){
        when(accountService.getAccountById(eq(1L))).thenReturn(sourceAccount);
        when(accountService.getAccountById(eq(2L))).thenReturn(targetAccount);

        transferDto.setAmount(BigDecimal.valueOf(110d));
        assertThrows(InsufficientBalanceException.class, () -> service.transferBalance(transferDto));

        assertThat(BigDecimal.valueOf(100d),  Matchers.comparesEqualTo(sourceAccount.getBalance()));
        assertThat(BigDecimal.valueOf(100d),  Matchers.comparesEqualTo(targetAccount.getBalance()));
    }

    @DisplayName("Given the same account as source and target, then balance is not transferred and SameAccountException is thrown.")
    @Test
    void transferMoney_withOneAccountAsSourceAndTarget(){
        when(accountService.getAccountById(eq(1L))).thenReturn(sourceAccount);
        when(accountService.getAccountById(eq(2L))).thenReturn(sourceAccount);

        transferDto.setAmount(BigDecimal.valueOf(110d));
        assertThrows(SameAccountException.class, () -> service.transferBalance(transferDto));

        assertThat(BigDecimal.valueOf(100d),  Matchers.comparesEqualTo(sourceAccount.getBalance()));
        assertThat(BigDecimal.valueOf(100d),  Matchers.comparesEqualTo(targetAccount.getBalance()));
    }

    @DisplayName("Given invalid account, AccountNotFoundException is thrown.")
    @Test
    void transferMoney_whenAccountNotFound(){
        when(accountService.getAccountById(eq(1L))).thenThrow(AccountNotFoundException.class);

        transferDto.setAmount(BigDecimal.valueOf(110d));
        assertThrows(AccountNotFoundException.class, () -> service.transferBalance(transferDto));
    }


}