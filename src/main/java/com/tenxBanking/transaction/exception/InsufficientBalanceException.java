package com.tenxBanking.transaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.tenxBanking.transaction.exception.ExceptionConstant.INSUFFICIENT_BALANCE;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = INSUFFICIENT_BALANCE)
public class InsufficientBalanceException extends RuntimeException {
}
