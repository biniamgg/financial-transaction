package com.tenxBanking.transaction.service;

import com.tenxBanking.transaction.exception.AccountNotFoundException;
import com.tenxBanking.transaction.model.Account;
import com.tenxBanking.transaction.repository.AccountRepository;
import org.springframework.stereotype.Service;


@Service
public class AccountService {

    private AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account getAccountById(long id){
        return repository.findById(id).orElseThrow(AccountNotFoundException::new);
    }
}
