package com.tenxBanking.transaction.service;

import com.tenxBanking.transaction.dto.TransferDto;
import com.tenxBanking.transaction.exception.InsufficientBalanceException;
import com.tenxBanking.transaction.exception.SameAccountException;
import com.tenxBanking.transaction.model.Account;
import com.tenxBanking.transaction.model.Transaction;
import com.tenxBanking.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionService {

    private final AccountService accountService;
    private final TransactionRepository repository;

    public TransactionService(AccountService accountService, TransactionRepository repository) {
        this.accountService = accountService;
        this.repository = repository;
    }

    @Transactional
    public Transaction transferBalance(TransferDto transferDto){
        Transaction transaction = mapToTransaction(transferDto);
        makeTransfer(transaction.getSourceAccount(), transaction.getTargetAccount(), transaction.getAmount());
        return repository.save(transaction);
    }

    private void makeTransfer(Account source, Account target, BigDecimal amount) {
        if (debitAccount(source, amount)){
            creditAccount(target, amount);
            return;
        }

        throw new InsufficientBalanceException();
    }

    private Transaction mapToTransaction(TransferDto transferDto) {

        Account source = accountService.getAccountById(transferDto.getSourceAccount());
        Account target = accountService.getAccountById(transferDto.getTargetAccount());

        if (source.equals(target)){
            throw new SameAccountException();
        }

        return new Transaction.TransactionBuilder()
                    .withSourceAccount(source)
                    .withTargetAccount(target)
                    .withAmount(transferDto.getAmount())
                    .withCurrency(transferDto.getCurrency())
                    .build();
    }


    private boolean debitAccount(Account account, BigDecimal amount){
        BigDecimal balance = account.getBalance();
        if (balance.compareTo(amount) > 0){
            account.setBalance(balance.subtract(amount));
            return true;
        }

        return false;
    }

    private void creditAccount(Account account, BigDecimal amount){
        BigDecimal balance = account.getBalance();
        account.setBalance(balance.add(amount));
    }
}
