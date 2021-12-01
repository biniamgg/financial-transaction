package com.tenxBanking.transaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.tenxBanking.transaction.exception.ExceptionConstant.SAME_ACCOUNT;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = SAME_ACCOUNT)
public class SameAccountException extends RuntimeException{
}
