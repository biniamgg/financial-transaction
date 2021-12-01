package com.tenxBanking.transaction.controller;

import com.tenxBanking.transaction.dto.TransferDto;
import com.tenxBanking.transaction.model.Transaction;
import com.tenxBanking.transaction.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @ApiOperation(value = "transfer balance between two accounts.", response = Transaction.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully transferred balance"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 406, message = "The Insufficient balance"),
            @ApiResponse(code = 400, message = "unacceptable request"),
    })
    @PostMapping(value = "/v1/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Transaction> transferBalance(@RequestBody TransferDto transferDto){
        return new ResponseEntity<>(service.transferBalance(transferDto), HttpStatus.OK);
    }

}
