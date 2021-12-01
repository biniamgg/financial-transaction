package com.tenxBanking.transaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.tenxBanking.transaction.exception.ExceptionConstant.ACCOUNT_NOT_FOUND;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ACCOUNT_NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {

}
