package com.picpaydesafio.controllers;

import com.picpaydesafio.domain.transaction.Transaction;
import com.picpaydesafio.dtos.TransactionDTO;
import com.picpaydesafio.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    private ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transacton) throws Exception {
        Transaction newTransaction = this.transactionService.createTransacion(transacton);
        return new ResponseEntity<>(newTransaction, HttpStatus.OK);
    }
}
