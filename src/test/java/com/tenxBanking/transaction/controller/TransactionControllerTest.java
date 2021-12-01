package com.tenxBanking.transaction.controller;

import com.tenxBanking.transaction.dto.TransferDto;
import com.tenxBanking.transaction.exception.AccountNotFoundException;
import com.tenxBanking.transaction.exception.InsufficientBalanceException;
import com.tenxBanking.transaction.exception.SameAccountException;
import com.tenxBanking.transaction.service.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class TransactionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransactionService service;

    private String body = "{\n" +
            "        \"sourceAccount\": 1,\n" +
            "        \"targetAccount\": 2,\n" +
            "        \"amount\": 200,\n" +
            "        \"currency\": \"GBP\"\n" +
            "    }";

  @DisplayName("Given valid account with sufficient balance, then balance is transferred and HttpStatus OK is returned.")
  @Test
  void transferBetweenTwoAccounts_happyPath() throws Exception {
        mvc.perform(post("/v1/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk());
    }

    @DisplayName("Given valid account with insufficient balance, then balance is not transferred and HttpStatus NOT_ACCEPTABLE is returned.")
    @Test
  void transferBetweenTwoAccounts_WhenInsufficientBalanceAvailable() throws Exception {
        when(service.transferBalance(any(TransferDto.class))).thenThrow(InsufficientBalanceException.class);
        mvc.perform(post("/v1/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNotAcceptable());

    }

    @DisplayName("Given the same account as source and target, then balance is not transferred and HttpStatus BAD_REQUEST is returned.")
    @Test
    void transferBalance_usingTheSameAccount() throws Exception {
        when(service.transferBalance(any(TransferDto.class))).thenThrow(SameAccountException.class);
        mvc.perform(post("/v1/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());

    }

    @DisplayName("Given invalid account, return HttpStatus NOT_FOUND.")
    @Test
    void transferBalance_whenAccountNotFound() throws Exception {
        when(service.transferBalance(any(TransferDto.class))).thenThrow(AccountNotFoundException.class);
        mvc.perform(post("/v1/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNotFound());

    }


}